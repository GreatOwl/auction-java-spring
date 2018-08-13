package com.auction.andrew.auction.bids;

import com.auction.andrew.auction.auctionitems.AuctionItem;

import org.springframework.beans.factory.annotation.Autowired;

public class BidValidator {
    
    private BidRepository bidRepository;

    @Autowired
    public BidValidator(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    public void validateBidder(BidInterface bid, BidInterface highestBid, AuctionItem item) {
        if (highestBid.getBidderId() != bid.getBidderId()) {
            validateReserve(bid, highestBid, item);
        }
    }

    public void validateReserve(BidInterface bid, BidInterface highestBid, AuctionItem item) {
        if (item.getReservePrice() > item.getCurrentBid()) {
            if (bid.getMaxAutoBidAmount() > item.getCurrentBid()) {
                bidRepository.placeBid(bid.getMaxAutoBidAmount(), item, bid);
            }
        } else {
            validateHighest(bid, highestBid, item);
        }
    }

    public void validateHighest(BidInterface bid, BidInterface highestBid, AuctionItem item) {
        if (bid.getMaxAutoBidAmount() > highestBid.getAmount()) {
            validateBest(bid, highestBid, item);
        }
    }

    public void validateBest(BidInterface bid, BidInterface highestBid, AuctionItem item) {
        Double best = highestBid.getMaxAutoBidAmount() + 1;
            Double newBest = bid.getMaxAutoBidAmount() + 1;
            if (bid.getMaxAutoBidAmount() >= best) {
                bidRepository.placeBid(best, item, bid);
            } else if (highestBid.getMaxAutoBidAmount() >= newBest) {
                bidRepository.placeHighestBid(newBest, bid.getMaxAutoBidAmount(), item, highestBid, bid);
            } else if (highestBid.getMaxAutoBidAmount().compareTo(bid.getMaxAutoBidAmount()) == 0) {
                Double lowAmount = bid.getMaxAutoBidAmount() - 1;
                bidRepository.placeHighestBid(highestBid.getMaxAutoBidAmount(), lowAmount, item, highestBid, bid);
            }
    }
}