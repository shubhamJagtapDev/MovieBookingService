package com.jshubham.bookmyshow.exceptions;

public class ShowSeatUnAvailableForBookingException extends RuntimeException {
    public ShowSeatUnAvailableForBookingException(String message) {
        super(message);
    }
}
