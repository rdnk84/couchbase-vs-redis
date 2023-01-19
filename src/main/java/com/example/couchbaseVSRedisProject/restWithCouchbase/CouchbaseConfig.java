package com.example.couchbaseVSRedisProject.restWithCouchbase;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.ClusterOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouchbaseConfig {

    @Value("${couchbase.host:couchbase://localhost}")
    private String connectionString;

    @Value("${couchbase.username:Administrator}")
    private String username;

    @Value("${couchbase.password:MyWorkProject11}")
    private String password;


    @Bean
    Cluster couchbaseCluster() {
        return Cluster.connect(connectionString, ClusterOptions.clusterOptions(username, password));
    }

}
