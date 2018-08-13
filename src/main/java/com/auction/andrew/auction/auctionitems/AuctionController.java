package com.auction.andrew.auction.auctionitems;

import java.util.ArrayList;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class AuctionController {

    private AuctionRepository auctionRepository;
    
    @Autowired
    public AuctionController (AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @RequestMapping(value="/auctionItems", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
	public ArrayList<AuctionItem> getAuctionItems() {
        return auctionRepository.getAuctionItems();
    }
    
    @RequestMapping(value="/auctionItems", method=RequestMethod.POST, produces="application/json;charset=UTF-8", consumes="application/json")
    @ResponseBody
	public ArrayList<AuctionItem> addAuctionItems(@RequestBody String requestbodyString) {
        auctionRepository.saveAuctionItem(requestbodyString);
        return auctionRepository.getAuctionItems();
	}

    @RequestMapping(value="/auctionItems/{auctionItemId}", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
	public AuctionItem getAuctionItem(@PathVariable String auctionItemId, HttpServletRequest request) {
        return auctionRepository.getAuctionItem(auctionItemId);
    }
}