package com.jshubham.bookmyshow.models;

import com.jshubham.bookmyshow.models.enums.Feature;
import com.jshubham.bookmyshow.models.enums.ScreenStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Screen extends BaseModel{
    private String name;

    @ManyToOne
    private Theatre theatre;

    @OneToMany(mappedBy = "screen")
    // @JoinColumn(name = "screen_id") // will create FK in Seats table with name screen_id
    private List<Seat> seats;

    @Enumerated(EnumType.ORDINAL)
    private ScreenStatus status;

    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    private List<Feature> features;
}
