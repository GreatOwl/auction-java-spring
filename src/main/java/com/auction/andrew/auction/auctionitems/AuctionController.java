package com.auction.andrew.auction.auctionitems;

import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class AuctionController {

    @RequestMapping(value="/auctionItems", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
	public AuctionItem[] getAuctionItems() {
        AuctionItem cat = new AuctionItem(0.00, 1000.00, "cat", "Furry feline mammal");
        AuctionItem dog = new AuctionItem(0.00, 1001.00, "cat", "Furry canine mammal");
        AuctionItem[] items = new AuctionItem[]{cat, dog};
        return items;
	}

    @RequestMapping(value="/auctionItems/{auctionItemId}", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
	public AuctionItem getAuctionItem(@PathVariable String auctionItemId) {;
        AuctionItem item = new AuctionItem(UUID.fromString(auctionItemId), 0.00, 1000.00, "cat", "Furry feline mammal");
        return item;
    }
    
    // private ResponseEntity<String> success(String body) {
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setContentType(MediaType.APPLICATION_JSON);
    //     return new ResponseEntity<String>(body, headers, HttpStatus.valueOf(200));
    // }
}