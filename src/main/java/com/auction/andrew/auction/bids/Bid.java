package com.auction.andrew.auction.bids;

import java.security.Timestamp;
import java.util.UUID;

import com.auction.andrew.auction.auctionitems.AuctionItem;
import com.auction.andrew.auction.bidders.Bidder;

public class Bid {

    private Timestamp id;
    private UUID auctionItemId;
    private Bidder bidder;

    public Bid() {
    }

}