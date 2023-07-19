package com.example.artaction.exception.bid;

public class InvalidBidPriceException extends IllegalArgumentException {

    public InvalidBidPriceException(String s) {
        super(s);
    }
}
