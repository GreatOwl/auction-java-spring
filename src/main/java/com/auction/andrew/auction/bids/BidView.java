package com.auction.andrew.auction.bids;
import java.util.UUID;

import com.auction.andrew.auction.bidders.Bidder;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

public class BidView implements BidInterface {//extends Bid {

    @JsonIgnore
    private BidInterface rawBid;
    private String bidderName;

    @JsonIgnore
    public BidInterface getRawBid() {
        return this.rawBid;
    }

    public void setTime() {
        getRawBid().setTime();
    }

    @JsonIgnore
    public String getTime() {
        return getRawBid().getTime();
    }

    @JsonIgnore
    public String getId() {
        return getRawBid().getId();
    }

    public void setId(UUID id) {
        getRawBid().setId(id);
    }

    @JsonSetter("id")
    public void setId(String id) {
        getRawBid().setId(id);
    }

    public void setId() {
        getRawBid().setId();
    }

    @JsonSetter("auctionItemId")
    public void setAuctionItemId(String auctionItemId) {
        getRawBid().setAuctionItemId(auctionItemId);
    }

    public String getAuctionItemId() {
        return getRawBid().getAuctionItemId();
    }

    @JsonSetter("bidderId")
    public void setBidderId(String bidderId) {
        getRawBid().setBidderId(bidderId);
    }

    @JsonIgnore
    public String getBidderId() {
        return getRawBid().getBidderId();
    }

    @JsonSetter("amount")
    public void setAmount(Double amount) {
        getRawBid().setAmount(amount);
    }

    @JsonGetter("amount")
    public Double getAmount() {
        return getRawBid().getAmount();
    }

    @JsonSetter("maxAutoBidAmount")
    public void setMaxAutoBidAmount(Double amount) {
        getRawBid().setMaxAutoBidAmount(amount);
    }

    public Double getMaxAutoBidAmount() {
        return getRawBid().getMaxAutoBidAmount();
    }

    @JsonSetter("bidderName")
    public void setBidderName(String name) {
        this.bidderName = name;
    }

    public String getBidderName() {
        return this.bidderName;
    }


    public BidView(BidInterface bid, Bidder bidder) {
        setBidderName(bidder.getName());
        this.rawBid = bid;
    }

    public BidView(String auctionItemId, String bidderId, Double Amount, Double Max, String bidderName) {
        // super(auctionItemId, bidderId, Amount, Max);
        this.rawBid = new Bid(auctionItemId, bidderId, Amount, Max);
    }

    public BidView(String auctionItemId, String bidderName, Double maxAutoBidAmount) {
        // super(auctionItemId, null, null, maxAutoBidAmount);
        setBidderName(bidderName);
    }
    
    public BidView() {
        // super();
        this.rawBid = new Bid();
        // this.rawBid.id = this.id;
        // super.id = this.id;
        // this.rawBid.time = this.time;
        // super.time = this.time;
    };
}