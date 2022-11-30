package com.example.couchbaseVSRedisProject.restWithCouchbase;

import com.couchbase.client.java.Cluster;

public class ClusterSingleton {

    public static Cluster INSTANCE;

    static String connectionString = "localhost";
    static String username = "Administrator";
    static String password = "MyWorkProject11";


    private ClusterSingleton() {
    }

    public static Cluster getInstance() {
        if (INSTANCE == null){
            INSTANCE = Cluster.connect("couchbase://" + connectionString, username, password);
        }
        return INSTANCE;
    }

}
