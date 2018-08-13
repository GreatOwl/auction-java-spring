package com.auction.andrew.auction.bids;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonSetter;

public interface BidInterface {

    public void setTime();

    public String getTime();

    public String getId();

    @JsonSetter("id")
    public void setId(String id);

    public void setId(UUID id);

    public void setId();

    @JsonSetter("auctionItemId")
    public void setAuctionItemId(String auctionItemId);

    public String getAuctionItemId();

    @JsonSetter("bidderId")
    public void setBidderId(String bidderId);

    public String getBidderId();

    @JsonSetter("amount")
    public void setAmount(Double amount);

    public Double getAmount();

    @JsonSetter("maxAutoBidAmount")
    public void setMaxAutoBidAmount(Double amount);

    public Double getMaxAutoBidAmount();
}