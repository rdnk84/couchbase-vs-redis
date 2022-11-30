package com.example.couchbaseVSRedisProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CouchbaseVSRedisProjectApplication {

    // Update these variables to point to your Couchbase Server instance and credentials.
    static String connectionString = "localhost";
    static String username = "Administrator";
    static String password = "MyWorkProject11";
    static String bucketName = "Bucket1";

    public static void main(String[] args) {
        // For a secure cluster connection, use `couchbases://<your-cluster-ip>` instead.
//        Cluster cluster = Cluster.connect("couchbase://" + connectionString, username, password);
        SpringApplication.run(CouchbaseVSRedisProjectApplication.class, args);
//
//        // Get a bucket reference
//        Bucket bucket = cluster.bucket(bucketName);
//        bucket.waitUntilReady(Duration.ofSeconds(10));
//        Collection newCollection = bucket.defaultCollection();
//
////        JsonObject person = JsonObject.create();
//        String movieId = "005_movie";
//        String userId = "001";
//        ArrayList<String> values = new ArrayList<>();
//
//        final JsonObject ratingJSON = JsonObject.create()
//                .put("movie_id", movieId)
//                .put("user_id", userId)
//                .put("value", values);
//
//        try {
//            bucket.defaultCollection().upsert("ratingId", ratingJSON);
//            System.out.println("OK");
//        } catch (Exception e) {
//            System.out.println("ERROR: " + e.getMessage());
//        }
    }

}
