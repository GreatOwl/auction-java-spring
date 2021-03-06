package com.auction.andrew.auction;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.PathMatchConfigurer;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication
@EnableAutoConfiguration
public class AuctionApplication {

	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseTrailingSlashMatch(false);
	}

	public static void main(String[] args) {
		SpringApplication.run(AuctionApplication.class, args);

	}
}
