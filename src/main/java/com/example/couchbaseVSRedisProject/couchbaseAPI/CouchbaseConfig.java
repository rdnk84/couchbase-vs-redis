package com.example.couchbaseVSRedisProject.couchbaseAPI;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.ClusterOptions;
import com.couchbase.client.java.manager.query.CreateQueryIndexOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class CouchbaseConfig {

    @Value("${couchbase.host:couchbase://localhost}")
    private String connectionString;

    @Value("${couchbase.username:Administrator}")
    private String username;

    @Value("${couchbase.password:MyWorkProject11}")
    private String password;

    static String bucketName = "Movies";


    @Bean
    Cluster couchbaseCluster() {
        Cluster cluster = Cluster.connect(connectionString, ClusterOptions.clusterOptions(username, password));
        cluster.bucket(bucketName);
//        cluster.queryIndexes().createIndex(bucketName, "index_name", Arrays.asList("movieName"));
        cluster.queryIndexes().createIndex(bucketName, "index_name", Collections.singletonList("movieName"),
                CreateQueryIndexOptions.createQueryIndexOptions().ignoreIfExists(true).deferred(false));
        return cluster;
    }



}
