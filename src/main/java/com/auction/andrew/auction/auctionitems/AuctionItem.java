package com.auction.andrew.auction.auctionitems;

import java.util.UUID;

public class AuctionItem {

    public class Item {
        private String itemId;
        private String description;

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

    public AuctionItem setCurrentBid(Double currentBid) {
        return new AuctionItem(auctionId, currentBid, reservePrice, item);
    }

    public Double getReservePrice() {
        return reservePrice;
    }

    public Item gItem() {
        return item;
    }


    protected AuctionItem(UUID auctionId, Double currentBid, Double reservePrice, Item item) {
        this.auctionId = auctionId;
        this.currentBid = currentBid;
        this.reservePrice = reservePrice;
        this.item = item;
    }
    

    public AuctionItem(Double currentBid, Double reservePrice, String itemId, String description) {
        this.auctionId = UUID.randomUUID();
        this.currentBid = currentBid;
        this.reservePrice = reservePrice;
        this.item = new Item(itemId, description);
    }
}