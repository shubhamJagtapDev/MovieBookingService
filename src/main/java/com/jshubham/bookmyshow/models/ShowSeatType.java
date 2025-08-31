package com.jshubham.bookmyshow.models;

import com.jshubham.bookmyshow.models.enums.SeatType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShowSeatType extends BaseModel{
    @ManyToOne
    private Show show;
    @Enumerated(EnumType.ORDINAL)
    private SeatType seatType;
    private double price;
}
/**
 * Show - X, Y, Z
 * SeatType - Gold, Silver, Platinum
 *
 * 1 ShowSeatType XGold will have one Show 'X' object
 * 1 Show X will have many ShowSeatType objs eg. XGold, XSilver, etc
 */
