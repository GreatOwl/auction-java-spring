package com.auction.andrew.auction.bidders;

import java.util.ArrayList;

import com.auction.andrew.auction.utility.errors.Forbidden;

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
public class BidderController {

    private BidderRepository bidderRepository;
    
    @Autowired
    public BidderController (BidderRepository bidderRepository) {
        this.bidderRepository = bidderRepository;
    }

    @RequestMapping(value="/bidders", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
	public ArrayList<Bidder> getBidders() {
        return bidderRepository.getBidders();
    }
    
    @RequestMapping(value="/bidders", method=RequestMethod.POST, produces="application/json;charset=UTF-8", consumes="application/json")
    @ResponseBody
	public Bidder addBidder(@RequestBody String requestbodyString) throws Forbidden {
        return bidderRepository.saveBidder(requestbodyString);
	}

    @RequestMapping(value="/bidders/{bidderId}", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
	public Bidder getBidder(@PathVariable String bidderId, HttpServletRequest request) {
        return bidderRepository.getBidder(bidderId);
    }
}