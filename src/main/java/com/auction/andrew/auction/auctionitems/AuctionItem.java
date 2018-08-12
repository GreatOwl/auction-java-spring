package com.auction.andrew.auction.auctionitems;

import java.io.IOException;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Document(collection = "auctionItems")
public class AuctionItem {

    public class Item {
        private String itemId;
        private String description;
    
        public Item() {}

        public Item(String itemId, String description) {
            this.itemId = itemId;
            this.description = description;
        }
    
        public String getItemId() {
            return itemId;
        }
    
        public String getDescription() {
            return description;
        }
    }

    private UUID auctionId;
    private Double currentBid;
    private Double reservePrice;
    private Item item;

    public UUID getAuctionId() {
        return auctionId;
    }

    public Double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(Double currentBid) {
        this.currentBid = currentBid;
    }

    public Double getReservePrice() {
        return reservePrice;
    }

    @JsonGetter("item")
    public Item getItem() {
        return item;
    }

    @JsonSetter("item")
    public void setItem(Item item) {
        this.item = item;
    }

    public AuctionItem() {}

    public AuctionItem(UUID auctionId, Double currentBid, Double reservePrice, Item item) {
        this.auctionId = auctionId;
        this.currentBid = currentBid;
        this.reservePrice = reservePrice;
        this.item = item;
    }
    
    public AuctionItem(String auctionId, Double currentBid, Double reservePrice, String itemId, String description) {
        this.auctionId = UUID.fromString(auctionId);
        this.currentBid = currentBid;
        this.reservePrice = reservePrice;
        this.item = new Item(itemId, description);
    }

    public AuctionItem(Double currentBid, Double reservePrice, String itemId, String description) {
        this.auctionId = UUID.randomUUID();
        this.currentBid = currentBid;
        this.reservePrice = reservePrice;
        this.item = new Item(itemId, description);
    }
}