package com.auction.andrew.auction.auctionitems;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class AuctionRepository {

    private MongoDatabase mongoDb;
    private ObjectMapper objectMapper;
    
    @Autowired
    public AuctionRepository(MongoDatabase mongoDb, ObjectMapper objectMapper) {
        this.mongoDb = mongoDb;
        this.objectMapper = objectMapper;
    }
    private MongoDatabase getDatabase() {
        return mongoDb;
    }

    public MongoCollection<Document> loadAuctionItems() {
        return getDatabase().getCollection("auctionItems", Document.class);
    }

    public AuctionItem getAuctionItem(String auctionItemId) {
        Iterable<Document> documents = loadAuctionItems().find(new Document("_id", auctionItemId));
        ArrayList<AuctionItem> items = parseItems(documents);
        return items.get(0);
    }

    private ArrayList<AuctionItem> parseItems(Iterable<Document> documents) {
        ArrayList<AuctionItem> rawItems = new ArrayList<AuctionItem>();
        for (Document document : documents) {
            try {
                AuctionItem item = parseAuctionItem(document.getString("auctionItem"));
                rawItems.add(item);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            }
        }
        return rawItems;
    }

    private AuctionItem parseAuctionItem(String json) throws JsonMappingException {
        try {
            AuctionItem item = objectMapper.readValue(json, AuctionItem.class);
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

    public ArrayList<AuctionItem> getAuctionItems() {
        MongoCollection<Document> documents = loadAuctionItems();
        return parseItems(documents.find());
    }

    public void updateAuctionItem(AuctionItem item) {
        MongoCollection<Document> items = loadAuctionItems();
        try {
            items.replaceOne(
                eq("_id", item.getAuctionItemId()), 
                new Document("auctionItem", objectMapper.writeValueAsString(item)));
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAuctionItem(String json) {
        MongoCollection<Document> items = loadAuctionItems();
        try {
            AuctionItem auctionItem = objectMapper.readValue(json, AuctionItem.class);
            items.insertOne(new Document("query", new Document("itemId", auctionItem.getItem().getItemId())
                    .append("description", auctionItem.getItem().getDescription())
                )
                .append("auctionItem", objectMapper.writeValueAsString(auctionItem))
                .append("_id", auctionItem.getAuctionItemId())
            );
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}