package com.auction.andrew.auction.bids;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Bid implements BidInterface {

    @JsonIgnore
    protected UUID id;
    // @JsonIgnore
    protected UUID bidderId;
    // @JsonIgnore
    protected String time;
    // @JsonIgnore
    protected Double amount;

    protected UUID auctionItemId;
    protected Double maxAutoBidAmount;

    public void setTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Timestamp stamp = new Timestamp(date.getTime());
        this.time = sdf.format(stamp);
    }

    public String getTime() {
        return time;
    }

    public String getId() {
        return id.toString();
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @JsonSetter("id")
    public void setId(String id) {
        setId(UUID.fromString(id));
    }

    public void setId() {
        setId(UUID.randomUUID());
    }

    @JsonSetter("auctionItemId")
    public void setAuctionItemId(String auctionItemId) {
        this.auctionItemId = UUID.fromString(auctionItemId);
    }

    public String getAuctionItemId() {
        return this.auctionItemId.toString();
    }

    @JsonSetter("bidderId")
    public void setBidderId(String bidderId) {
        this.bidderId = UUID.fromString(bidderId);
    }

    public String getBidderId() {
        return this.bidderId.toString();
    }

    @JsonSetter("amount")
    public void setAmount(Double amount) {
        this.amount = amount;
        if (this.amount == null) {
            this.amount = 0.00;
        }
    }

    public Double getAmount() {
        if (this.amount == null) {
            return 0.00;
        }
        return this.amount;
    }

    @JsonSetter("maxAutoBidAmount")
    public void setMaxAutoBidAmount(Double amount) {
        this.maxAutoBidAmount = amount;
    }

    public Double getMaxAutoBidAmount() {
        return this.maxAutoBidAmount;
    }

    public Bid(String auctionItemId, String bidderId, Double Amount, Double Max) {
        setTime();
        setId();
        setBidderId(bidderId);
        setAuctionItemId(auctionItemId);
        setMaxAutoBidAmount(Max);
        setAmount(amount);
    }

    public Bid() {
        setTime();
        setId();
    }

}