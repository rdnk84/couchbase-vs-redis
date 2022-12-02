package com.example.couchbaseVSRedisProject.restWithCouchbase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.example.couchbaseVSRedisProject.POJO.POJODoc;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CouchbaseService {

    static String bucketName = "Bucket1";

    public POJODoc handleUpsertData(ObjectNode object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String fromObjToString = object.toPrettyString();

        POJODoc newObj = objectMapper.readValue(fromObjToString, POJODoc.class);
        Cluster cluster = ClusterSingleton.getInstance();
        Bucket bucket = cluster.bucket(bucketName);
        UUID uuid = UUID.randomUUID();
        String newIDGenerated = uuid.toString();
//        newObj.setId(newIDGenerated);
//        newObj.setSomeText("blablabla");
        try {
            bucket.defaultCollection().upsert(newIDGenerated, object);
            System.out.println("OK");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return newObj;
//    cluster.disconnect();
    }


    public POJODoc retrievingDoc(String key) throws JsonProcessingException {
        Cluster cluster = ClusterSingleton.getInstance();
        Bucket bucket = cluster.bucket(bucketName);
        Collection newCollection = bucket.defaultCollection();
        GetResult result = newCollection.get(key);
        JsonObject obj = result.contentAsObject();
        ObjectMapper objectMapper = new ObjectMapper();
        String strObj = obj.toString();
        POJODoc retrievedDocument = objectMapper.readValue(strObj, POJODoc.class);
//    cluster.disconnect();
        return retrievedDocument;
    }

//    public String handleUpsertData(ObjectNode object) {
//
//        Cluster cluster = ClusterSingleton.getInstance();
//        Bucket bucket = cluster.bucket(bucketName);
//        UUID uuid = UUID.randomUUID();
//        String newIDGenerated = uuid.toString();
//
//        try {
//            //как я меняю id?
//            bucket.defaultCollection().upsert(newIDGenerated, object);
//            System.out.println("OK");
//        } catch (Exception e) {
//            System.out.println("ERROR: " + e.getMessage());
//        }
//        return newObj;
////    cluster.disconnect();
//    }


}
