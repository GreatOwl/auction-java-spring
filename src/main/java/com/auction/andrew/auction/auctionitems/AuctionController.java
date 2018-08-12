package com.auction.andrew.auction.auctionitems;


import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        
        // request.getp
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            AuctionItem item = objectMapper.readValue(requestbodyString, AuctionItem.class);
            auctionRepository.saveAuctionItem(item);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // AuctionItem cat = new AuctionItem(0.00, 1000.00, "cat", "Furry feline mammal");
        // AuctionItem dog = new AuctionItem(0.00, 1001.00, "cat", "Furry canine mammal");
        return auctionRepository.getAuctionItems();
	}

    @RequestMapping(value="/auctionItems/{auctionItemId}", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
	public AuctionItem getAuctionItem(@PathVariable String auctionItemId) {
        return auctionRepository.getAuctionItem(auctionItemId);
    }
}