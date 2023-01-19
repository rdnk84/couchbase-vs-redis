package com.example.couchbaseVSRedisProject;

public class DocumentNotFoundException extends Exception{

    public DocumentNotFoundException(String documentKey) {
        super("" + documentKey + "");
    }
}
