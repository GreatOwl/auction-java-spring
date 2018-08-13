package com.auction.andrew.auction.bids;

import java.io.IOException;
import java.util.ArrayList;

import com.auction.andrew.auction.auctionitems.AuctionItem;
import com.auction.andrew.auction.auctionitems.AuctionRepository;
import com.auction.andrew.auction.bidders.Bidder;
import com.auction.andrew.auction.bidders.BidderRepository;
import com.auction.andrew.auction.utility.errors.Forbidden;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidRepository {

    private MongoDatabase mongoDb;
    private ObjectMapper objectMapper;
    private AuctionRepository auctionRepository;
    
    @Autowired
    public BidRepository(MongoDatabase mongoDb, ObjectMapper objectMapper, BidderRepository bidderRepository, AuctionRepository auctionRepository) {
        this.mongoDb = mongoDb;
        this.objectMapper = objectMapper;
        this.auctionRepository = auctionRepository;
    }

    private MongoDatabase getDatabase() {
        return mongoDb;
    }

    public MongoCollection<Document> loadBids() {
        return getDatabase().getCollection("bids", Document.class);
    }

    public ArrayList<BidInterface> getBids() {
        Iterable<Document> documents = loadBids().find();
        return parseBids(documents);
    }

    public ArrayList<BidInterface> getItemBids(String auctionItemId) {
        Iterable<Document> documents = loadBids().find(Document.parse("{\"query.auctionItemId\": \"" + auctionItemId + "\"}"));
        ArrayList<BidInterface> bidders = parseBids(documents);
        bidders.sort((BidInterface a, BidInterface b) -> {
            Integer comparison = b.getAmount().compareTo(a.getAmount());
            if (comparison == 0) {
                return b.getMaxAutoBidAmount().compareTo(a.getMaxAutoBidAmount());
            }
            return comparison;
        });
        return bidders;
    }

    public ArrayList<BidInterface> getBidderBids(String bidderId) {
        Iterable<Document> documents = loadBids().find(new Document("query", new Document("bidderId", bidderId)));
        ArrayList<BidInterface> bidders = parseBids(documents);
        return bidders;
    }

    public BidInterface getBid(String bidId) {
        Iterable<Document> documents = loadBids().find(new Document("_id", bidId));
        ArrayList<BidInterface> bidders = parseBids(documents);
        return bidders.get(0);
    }

    public ArrayList<BidInterface> getBids(String auctionItemId, String time) {
        Iterable<Document> documents = loadBids().find(new Document("query", new Document("auctionItemId", auctionItemId)
            .append("time", time))
        );
        return parseBids(documents);
    }

    public ArrayList<BidInterface> getBids(String auctionItemId) {
        Iterable<Document> documents = loadBids().find(Document.parse("{\"query.auctionItemId\": \"" + auctionItemId + "\"}"));
        //new Document("query", new Document("auctionItemId", auctionItemId)));
        return parseBids(documents);
    }

    public BidInterface saveBid(BidInterface bid) {
        MongoCollection<Document> bids = loadBids();
        try {
            // Bid bid = parseBid(json);
            ArrayList<BidInterface> bidsFound = getBids(bid.getAuctionItemId(), bid.getTime());
            if (bidsFound.size() == 0) {
                bids.insertOne(new Document("query", new Document("auctionItemId", bid.getAuctionItemId())
                        .append("bidderId", bid.getBidderId())
                        .append("time", bid.getTime())
                    )
                    .append("bid", objectMapper.writeValueAsString(bid))
                    .append("_id", bid.getId())
                );
                return bid;
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new Forbidden();
    }

    private ArrayList<BidInterface> parseBids(Iterable<Document> documents) {
        ArrayList<BidInterface> bids = new ArrayList<BidInterface>();
        for (Document document : documents) {
            // document.values();
            try {
                bids.add(parseBid(document.getString("bid")));
            } catch (JsonMappingException e) {
                e.printStackTrace();
            }
        }
        return bids;
    }

    public Bid parseBid(String json) throws JsonMappingException {
        try {
            // String json = document.getString("bidder");
            Bid item = objectMapper.readValue(json, Bid.class);
            return item;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw e;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new JsonMappingException("Failed to parse bid");
    }

    public BidView parseBid(String json, BidderRepository repository) throws JsonMappingException {
        try {
            // String json = document.getString("bidder");
            BidView item = objectMapper.readValue(json, BidView.class);
            ArrayList<Bidder> bidders = repository.getBidders(item.getBidderName());
            if (bidders.size() > 0) {
                Bidder bidder = bidders.get(0);
                item.setBidderId(bidder.getId());
            }
            return item;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw e;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new JsonMappingException("Failed to parse bid");
    }

    public void placeBid(Double amount, AuctionItem item, BidInterface bid) {
        item.setCurrentBid(amount);
        bid.setAmount(amount);
        auctionRepository.updateAuctionItem(item);
        saveBid(bid);
    }

    public void placeHighestBid(
        Double highAmount, 
        Double lowAmount, 
        AuctionItem item, 
        BidInterface highestBid, 
        BidInterface bid
    ) {
        highestBid.setId();
        placeBid(highAmount, item, highestBid);
        bid.setAmount(lowAmount);
        saveBid(bid);
    }
}