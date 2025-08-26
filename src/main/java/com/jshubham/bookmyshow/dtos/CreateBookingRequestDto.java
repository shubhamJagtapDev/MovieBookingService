package com.jshubham.bookmyshow.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CreateBookingRequestDto {
    private long userId;
    private long showId;
    private List<Long> showSeatIds;
}
