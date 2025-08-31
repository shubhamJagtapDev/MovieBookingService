package com.jshubham.bookmyshow.exceptions;

public class FeatureNotSupportedByScreen extends RuntimeException {
    public FeatureNotSupportedByScreen(String message) {
        super(message);
    }
}
