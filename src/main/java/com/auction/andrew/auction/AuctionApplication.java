package com.auction.andrew.auction;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.PathMatchConfigurer;

import com.mongodb.client.MongoClients;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication
@EnableAutoConfiguration
public class AuctionApplication {

	// @Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseTrailingSlashMatch(false);
	}

	public static void main(String[] args) {
		// SpringApplication app = new SpringApplication(AuctionApplication.class);
		// app.setWebApplicationType(WebApplicationType.SERVLET);
		SpringApplication.run(AuctionApplication.class, args);
		
	}
}
