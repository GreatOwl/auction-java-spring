package com.auction.andrew.auction.auctionitems;


import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

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

    @JsonSetter("auctionId")
    public void setAuctionId(String auctionId) {
        setAuctionId(UUID.fromString(auctionId));
    }

    private void setAuctionId(UUID auctionId) {
        this.auctionId = auctionId;
    }

    public void setAuctionId() {
        setAuctionId(UUID.randomUUID());
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

    public AuctionItem() {
        setAuctionId();
        this.currentBid = 0.00;
    }

    public AuctionItem(UUID auctionId, Double currentBid, Double reservePrice, Item item) {
        setAuctionId(auctionId);
        this.currentBid = currentBid;
        this.reservePrice = reservePrice;
        this.item = item;
    }
    
    public AuctionItem(String auctionId, Double currentBid, Double reservePrice, String itemId, String description) {
        setAuctionId(auctionId);
        this.currentBid = currentBid;
        this.reservePrice = reservePrice;
        this.item = new Item(itemId, description);
    }

    public AuctionItem(Double currentBid, Double reservePrice, String itemId, String description) {
        setAuctionId();
        this.currentBid = currentBid;
        this.reservePrice = reservePrice;
        this.item = new Item(itemId, description);
    }

    public AuctionItem(Double reservePrice, String itemId, String description) {
        setAuctionId();
        this.currentBid = 0.00;
        this.reservePrice = reservePrice;
        this.item = new Item(itemId, description);
    }
}