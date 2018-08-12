package com.auction.andrew.auction.auctionitems;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuctionClient extends MongoClient {

    @Autowired
    public AuctionClient(final MongoClientURI uri) {
        super(uri);
    }

    public MongoDatabase getDatabase() {
        return super.getDatabase("auction");
    }
}