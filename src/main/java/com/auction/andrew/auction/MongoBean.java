package com.auction.andrew.auction;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MongoBean {

    // private final MongoDbFactory mongo;

    // @Autowired
    public MongoBean() {//MongoDbFactory mongo) {
        // this.mongo = mongo;
    }

    @Bean
    public MongoClient client() {
        MongoClientURI uri = new MongoClientURI("mongodb://root:example@andrew_mongo:27017");
        MongoClient client = new com.mongodb.MongoClient(uri);
        return client;
        // MongoCredential credential = MongoCredential.createCredential("root", "auction", "example".toCharArray());
        // ServerAddress address = new ServerAddress("andrew_mongo", 27017);
        // CodecRegistry registry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        // // MongoClientSettings settings = MongoClientSettings.builder()
        //     // .codecRegistry(registry)
        //     // .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(address)))
        //     // .credential(credential)
        //     // .build();
        // return MongoClients.create(settings);
    }

    @Bean
    @Autowired
    public MongoDatabase database(MongoClient client){//, CodecRegistry registry) {
        MongoDatabase database = client.getDatabase("auction");
        // database.withCodecRegistry(registry);
        return database;
        // return MongoClients.create("mongodb://root:example@andrew_mongo:27017").getDatabase("auction");
        // MongoDatabase db = mongo.getDb("auction");
        // return db;
    }

    // @Bean
    // public CodecRegistry getRegistry()
    // {
    //     return fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    // }
}