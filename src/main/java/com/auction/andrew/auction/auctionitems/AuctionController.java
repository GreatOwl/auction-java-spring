package com.auction.andrew.auction.auctionitems;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class AuctionController {

    @RequestMapping(value="/auctionItems", method=RequestMethod.GET)
	public ResponseEntity<String> getAuctionItems() {
        return success("{[{\"auctionItemId\": \"1234\"},{\"auctionItemId\": \"5678\"}]}");
	}

    @RequestMapping(value="/auctionItems/{auctionItemId}", method=RequestMethod.GET)
	public ResponseEntity<String> getAuctionItem(@PathVariable String auctionItemId) {
        return success("{\"auctionItemId\": \"" + auctionItemId + "\"}");
    }
    
    private ResponseEntity<String> success(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String>(body, headers, HttpStatus.valueOf(200));
    }
}