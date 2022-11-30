package com.example.couchbaseVSRedisProject.restWithCouchbase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.MutationResult;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

@Service
public class CouchbaseService {

    static String bucketName = "Bucket1";

public void handleUpsertData(ObjectNode object) {

    Cluster cluster = ClusterSingleton.getInstance();
    Bucket bucket = cluster.bucket(bucketName);
//    MutationResult
    try {
        //как я меняю id?
        bucket.defaultCollection().upsert("ratingId", object);
        System.out.println("OK");
    } catch (Exception e) {
        System.out.println("ERROR: " + e.getMessage());
    }

//    cluster.disconnect();
}

public JsonObject retrievingDoc(String key) {
    Cluster cluster = ClusterSingleton.getInstance();
    Bucket bucket = cluster.bucket(bucketName);
    Collection newCollection = bucket.defaultCollection();
    GetResult result = newCollection.get(key);
    JsonObject obj = result.contentAsObject();
//    String strObj = obj.toString();
//    cluster.disconnect();
    return obj;
}

}
