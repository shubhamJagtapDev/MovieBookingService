package com.jshubham.bookmyshow.controllers;

import com.jshubham.bookmyshow.dtos.CreateBookingRequestDto;
import com.jshubham.bookmyshow.dtos.CreateBookingResponseDto;
import com.jshubham.bookmyshow.dtos.ResponseStatus;
import com.jshubham.bookmyshow.models.Booking;
import com.jshubham.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public CreateBookingResponseDto createBooking(CreateBookingRequestDto requestDto) {
        CreateBookingResponseDto responseDto = new CreateBookingResponseDto();
        try {
            Booking booking = bookingService.createBooking(requestDto.getUserId(), requestDto.getShowSeatIds(),
                                                            requestDto.getShowId());
            responseDto.setBookingId(booking.getId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
