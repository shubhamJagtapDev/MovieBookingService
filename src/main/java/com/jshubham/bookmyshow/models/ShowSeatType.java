package com.jshubham.bookmyshow.models;

import com.jshubham.bookmyshow.models.enums.SeatType;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShowSeatType extends BaseModel{
    private Show show;
    private SeatType seatType;
    private int price;
}
