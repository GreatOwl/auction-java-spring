package com.auction.andrew.auction.auctionitems;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionRepository {

    private MongoDatabase mongoDatabase;
    
    @Autowired
    public AuctionRepository(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public MongoCollection<Document> loadAuctionItems() {
        return mongoDatabase.getCollection("auctionItems", Document.class);
    }

    public ArrayList<AuctionItem> getAuctionItems() {
        MongoCollection<Document> documents = loadAuctionItems();
        ArrayList<AuctionItem> rawItems = new ArrayList<AuctionItem>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Document document : documents.find()) {
            // document.values();
            try {
                String json = document.getString("auctionItem");
                AuctionItem item = objectMapper.readValue(json, AuctionItem.class);
                rawItems.add(item);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonGenerationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rawItems;
    }

    public void saveAuctionItem(AuctionItem auctionItem) {
        MongoCollection<Document> items = loadAuctionItems();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String auctionJson = objectMapper.writeValueAsString(auctionItem);
            items.insertOne(new Document("auctionItem", auctionJson));//objectMapper.writeValueAsString(auctionItem)));
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}