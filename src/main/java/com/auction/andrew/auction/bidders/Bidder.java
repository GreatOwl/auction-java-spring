package com.auction.andrew.auction.bidders;

import java.util.UUID;

public class Bidder {
    private UUID id;
    private String name;

    public void setId() {
        this.id = UUID.randomUUID();
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    Bidder() {
        setId();
    }

    Bidder(String name) {
        setId();
        setName(name);
    }
}