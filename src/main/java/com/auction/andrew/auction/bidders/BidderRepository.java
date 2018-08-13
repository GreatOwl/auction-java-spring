package com.auction.andrew.auction.bidders;

import java.io.IOException;
import java.util.ArrayList;

import com.auction.andrew.auction.utility.errors.Forbidden;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidderRepository {

    private MongoDatabase mongoDb;
    private ObjectMapper objectMapper;
    
    @Autowired
    public BidderRepository(MongoDatabase mongoDb, ObjectMapper objectMapper) {
        this.mongoDb = mongoDb;
        this.objectMapper = objectMapper;
    }

    private MongoDatabase getDatabase() {
        return mongoDb;
    }

    public MongoCollection<Document> loadBidders() {
        return getDatabase().getCollection("bidders", Document.class);
    }

    public ArrayList<Bidder> getBidders() {
        Iterable<Document> documents = loadBidders().find();
        return parseBidders(documents);
    }

    public ArrayList<Bidder> getBidders(String name) {
        Iterable<Document> documents = loadBidders().find(new Document("query", new Document("name", name)));
        ArrayList<Bidder> bidders = parseBidders(documents);
        return bidders;
    }

    public Bidder getBidder(String bidderId) {
        Iterable<Document> documents = loadBidders().find(new Document("_id", bidderId));
        ArrayList<Bidder> bidders = parseBidders(documents);
        return bidders.get(0);
    }

    public Bidder saveBidder(String json) {
        MongoCollection<Document> bidders = loadBidders();
        try {
            Bidder bidder = parseBidder(json);
            ArrayList<Bidder> biddersFound = getBidders(bidder.getName());
            if (biddersFound.size() == 0) {
                bidders.insertOne(new Document("query", new Document("name", bidder.getName()))
                    .append("bidder", objectMapper.writeValueAsString(bidder))
                    .append("_id", bidder.getId())
                );
                return bidder;
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new Forbidden();
    }

    private ArrayList<Bidder> parseBidders(Iterable<Document> documents) {
        ArrayList<Bidder> bidders = new ArrayList<Bidder>();
        for (Document document : documents) {
            // document.values();
            try {
                bidders.add(parseBidder(document.getString("bidder")));
            } catch (JsonMappingException e) {
                e.printStackTrace();
            }
        }
        return bidders;
    }

    private Bidder parseBidder(String json) throws JsonMappingException {
        try {
            // String json = document.getString("bidder");
            Bidder item = objectMapper.readValue(json, Bidder.class);
            return item;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw e;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new JsonMappingException("Failed to parse bidder");
    }
}