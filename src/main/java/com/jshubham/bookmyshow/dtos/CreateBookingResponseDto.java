package com.jshubham.bookmyshow.dtos;

import lombok.Data;

@Data
public class CreateBookingResponseDto {
    private long bookingId;
    private ResponseStatus responseStatus;
}
