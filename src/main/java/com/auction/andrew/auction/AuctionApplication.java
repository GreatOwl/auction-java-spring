package com.auction.andrew.auction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.*;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class AuctionApplication {

	@RequestMapping("/auctionItems/")
	String getAuctionItems() {
		return "{[{\"auctionItemId\": \"1234\"},{\"auctionItemId\": \"5678\"}]}";
	}

	@RequestMapping("/auctionItems/{auctionItemId}")
	String getAuctionItem(String auctionItemId) {
		//Todo: Learn how to pass in parameters.
		return "{\"auctionItemId\": \"" + auctionItemId + "\"}";
	}

	public static void main(String[] args) {
		SpringApplication.run(AuctionApplication.class, args);
	}
}
