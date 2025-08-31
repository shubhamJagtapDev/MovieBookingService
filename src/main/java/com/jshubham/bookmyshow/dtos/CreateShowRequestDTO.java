package com.jshubham.bookmyshow.dtos;

import com.jshubham.bookmyshow.models.enums.Feature;
import com.jshubham.bookmyshow.models.enums.SeatType;
import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.Date;
import java.util.List;

@Data
public class CreateShowRequestDTO {
    private Long movieId;
    private Long userId;
    private Long screenId;
    private Date startTime;
    private Date endTime;
    private List<Feature> features;
    private List<Pair<SeatType, Double>> pricingConfig;
}
