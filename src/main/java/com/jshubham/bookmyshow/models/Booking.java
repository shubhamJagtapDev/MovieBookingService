package com.jshubham.bookmyshow.models;

import com.jshubham.bookmyshow.models.enums.BookingStatus;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Booking extends BaseModel{ // Ticket
    private String bookingNumber;
    private User user;
    private Show show; // maybe skipped as the ShowSeat will have show object
    private List<ShowSeat> showSeats;
    private int amount;
    private List<Payment> payments;
    private BookingStatus bookingStatus;
}
