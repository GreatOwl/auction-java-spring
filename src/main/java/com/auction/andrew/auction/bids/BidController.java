package com.auction.andrew.auction.bids;

import java.util.ArrayList;

import com.auction.andrew.auction.auctionitems.AuctionItem;
import com.auction.andrew.auction.auctionitems.AuctionRepository;
import com.auction.andrew.auction.bidders.Bidder;
import com.auction.andrew.auction.bidders.BidderRepository;
import com.auction.andrew.auction.utility.errors.Forbidden;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class BidController {

    private BidValidator bidValidator;
    private BidRepository bidRepository;
    private BidderRepository bidderRepository;
    private AuctionRepository auctionRepository;
    
    @Autowired
    public BidController (BidValidator bidValidator, BidRepository bidRepository, BidderRepository bidderRepository, AuctionRepository auctionRepository) {
        this.bidValidator = bidValidator;
        this.bidRepository = bidRepository;
        this.bidderRepository = bidderRepository;
        this.auctionRepository = auctionRepository;
    }

    @RequestMapping(
        value="/bids", 
        method=RequestMethod.GET, 
        produces="application/json;charset=UTF-8")
    @ResponseBody
	public ArrayList<BidInterface> getBids() {
        return renderBids(bidRepository.getBids());
    }

    @RequestMapping(
        value="/bids", 
        method=RequestMethod.GET,
        params="auctionItemId",
        produces="application/json;charset=UTF-8")
    @ResponseBody
    public ArrayList<BidInterface> getBids(@RequestParam("auctionItemId") String auctionItemId) {
        return renderBids(bidRepository.getItemBids(auctionItemId));
    }
    
    @RequestMapping(value="/bids", method=RequestMethod.POST, produces="application/json;charset=UTF-8", consumes="application/json")
    @ResponseBody
	public ArrayList<BidInterface> addBid(@RequestBody String requestbodyString) throws Forbidden {
        try {
            BidInterface bid = bidRepository.parseBid(requestbodyString, bidderRepository).getRawBid();
            AuctionItem item = auctionRepository.getAuctionItem(bid.getAuctionItemId());
            ArrayList<BidInterface> bids = bidRepository.getItemBids(bid.getAuctionItemId());
            if (bids.size() > 0) {
                BidInterface highestBid = bids.get(0);
                bidValidator.validateBidder(bid, highestBid, item);
            } else {
                //Ensures bid hits item reserve validation if no bids have been placed.
                bidValidator.validateBidder(bid, bid, item);
            }
            return renderBids(bidRepository.getItemBids(bid.getAuctionItemId()));//solve
        } catch (JsonMappingException e) {
            e.printStackTrace();
        }
        
        throw new Forbidden();
    }

    @RequestMapping(value="/bids/{bidId}", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
	public BidInterface getBid(@PathVariable String bidId, HttpServletRequest request) {
        BidInterface bid = bidRepository.getBid(bidId);
        return renderBid(bid);
    }

    private BidInterface renderBid(BidInterface bid) {
        Bidder bidder = bidderRepository.getBidder(bid.getBidderId());
        return new BidView(bid, bidder);
    }

    private ArrayList<BidInterface> renderBids(ArrayList<BidInterface> rawBids) {
        ArrayList<BidInterface> bids = new ArrayList<BidInterface>();
        for (BidInterface bid : rawBids) {
            bids.add(renderBid(bid));
        } 
        return bids;
    }
}