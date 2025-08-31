package com.jshubham.bookmyshow.models;

import com.jshubham.bookmyshow.models.enums.Feature;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "shows") // "show" is reserved keyword, so we need to use shows name for table
public class Show extends BaseModel{
    @ManyToOne
    private Movie movie;

    private Date startTime;
    private Date endTime;

    @OneToMany(mappedBy = "show")
    private List<ShowSeat> showSeats;
    @OneToMany(mappedBy = "show")
    private List<ShowSeatType> showSeatTypes;

    @ManyToOne
    private Screen screen;

    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    private List<Feature> features;
}
