package com.example.couchbaseVSRedisProject.couchbaseAPI;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonCreator;
import com.couchbase.client.core.error.IndexNotFoundException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.manager.search.SearchIndex;
import com.example.couchbaseVSRedisProject.couchbaseAPI.ftsSearch.SearchIndexMainClass;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@Component
public class BucketWrapper {

    private static final Logger logger = LoggerFactory.getLogger(BucketWrapper.class);
    static String bucketName = "Movies";
    private final Cluster couchbaseCluster;

    @Autowired
    public BucketWrapper(Cluster couchbaseCluster) {
        this.couchbaseCluster = couchbaseCluster;
    }



    @PostConstruct
    private void init() throws IOException {
        initFTSIndexes();
    }

    public void initFTSIndexes() throws IOException {
        Bucket bucket = couchbaseCluster.bucket(bucketName);
        createFTSIndex(bucket, "Description");
    }

    public void createFTSIndex(Bucket bucket, String fieldName) throws IOException {
        String indexName = "movs";
        ObjectMapper objectMapper = new ObjectMapper();
        if (isIndexExist(bucket, indexName, fieldName)) {
            return;
        }
        String indexConfiguration = getIndexConfiguration(bucket, fieldName);
        logger.info("indexConf is: " + indexConfiguration);
        SearchIndex searchIndex = objectMapper.readValue(indexConfiguration, CouchbaseSearchIndex.class);
        couchbaseCluster.searchIndexes().upsertIndex(searchIndex);
        logger.info("Full text search index for bucket with name 'movs' was created successfully.");
    }


    private String getIndexConfiguration(Bucket bucket, String fieldName) {
        String path = "C:/Users/elru0121/Desktop/TBAPI/movs.json";
        String json = null;
        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(path)) {
            int data = fis.read();
            while (data != -1) {
                stringBuilder.append((char) data);
                data = fis.read();
            }
            json = new String(stringBuilder);
        } catch (IOException e) {
            logger.debug("Index config is empty, file not found");
        }
        return json;
    }

    public boolean isIndexExist(Bucket bucket, String index, String fieldName) {
        try {
            couchbaseCluster.searchIndexes().getIndex(index);
            logger.info("FTS already exists");
            return true;
        } catch (IndexNotFoundException e) {
            return false;
        } catch (Exception e) {
            logger.info("NPE");
//            logger.error("An exception occurred during getting fts indexes", e);
            return false;
        }
    }

    public static class CouchbaseSearchIndex extends SearchIndex {
        @JsonCreator
        @JsonIgnoreProperties(ignoreUnknown = true)
        public CouchbaseSearchIndex(@JsonProperty("name") String name,
                                    @JsonProperty("sourceName") String sourceName,
                                    @JsonProperty("uuid") String uuid,
                                    @JsonProperty("type") String type,
                                    @JsonProperty("params") Map<String, Object> params,
                                    @JsonProperty("sourceUUID") String sourceUuid,
                                    @JsonProperty("sourceParams") Map<String, Object> sourceParams,
                                    @JsonProperty("sourceType") String sourceType,
                                    @JsonProperty("planParams") Map<String, Object> planParams) {
            super(uuid, name, type, params, sourceUuid, sourceName, sourceParams, sourceType, planParams);
        }
    }
}
