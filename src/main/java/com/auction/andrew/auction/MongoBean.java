package com.auction.andrew.auction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoBean {
    public MongoBean() {
    }

    @Bean
    public MongoClientURI mongoUri() {
        //Todo: pull these parameters into build time configuration. Prod values should never be committed.
        return new MongoClientURI("mongodb://root:example@andrew_mongo:27017");
    }

    @Bean
    @Autowired
    public MongoClient client(MongoClientURI uri) {
        return new MongoClient(uri);
    }

    @Bean
    @Autowired
    public MongoDatabase database(MongoClient client) {
        return client.getDatabase("auction");
    }

    public ObjectMapper mapper() {
        return new ObjectMapper();
    }
}