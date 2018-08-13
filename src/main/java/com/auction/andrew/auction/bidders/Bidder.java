package com.auction.andrew.auction.bidders;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "auctionItems")
public class Bidder {
    private UUID id;
    private String name;

    public void setId() {
        this.id = UUID.randomUUID();
    }

    @JsonSetter("id")
    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    @JsonGetter("id")
    public String getId() {
        return id.toString();
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    Bidder() {
        setId();
    }

    Bidder(String name) {
        setId();
        setName(name);
    }
}