package com.auction.andrew.auction.auctionitems;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.async.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.mongodb.client.model.Filters.*;

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

    public AuctionItem getAuctionItem(String auctionItemId) {
        Iterable<Document> documents = loadAuctionItems().find(new Document("_id", auctionItemId));
        ArrayList<AuctionItem> items = parseItems(documents);
        return items.get(0);
    }

    private ArrayList<AuctionItem> parseItems(Iterable<Document> documents) {
        ArrayList<AuctionItem> rawItems = new ArrayList<AuctionItem>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Document document : documents) {
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

    public ArrayList<AuctionItem> getAuctionItems() {
        MongoCollection<Document> documents = loadAuctionItems();
        return parseItems(documents.find());
    }

    public void saveAuctionItem(AuctionItem auctionItem) {
        MongoCollection<Document> items = loadAuctionItems();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String auctionJson = objectMapper.writeValueAsString(auctionItem);
            items.insertOne(new Document("query", new Document("itemId", auctionItem.getItem().getItemId())
                    .append("description", auctionItem.getItem().getDescription())
                )
                .append("auctionItem", auctionJson)
                .append("_id", auctionItem.getAuctionItemId())
            );//objectMapper.writeValueAsString(auctionItem)));
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}