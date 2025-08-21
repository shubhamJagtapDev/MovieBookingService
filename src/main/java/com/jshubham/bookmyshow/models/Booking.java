package com.jshubham.bookmyshow.models;

import com.jshubham.bookmyshow.models.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Booking extends BaseModel{ // Ticket
    private String bookingNumber;
    @ManyToOne
    private User user;

    @ManyToOne
    private Show show; // maybe skipped as the ShowSeat will have show object

    @ManyToMany
    private List<ShowSeat> showSeats;
    private int amount;

    @OneToMany
    private List<Payment> payments;

    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;
}
