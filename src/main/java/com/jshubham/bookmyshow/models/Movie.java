package com.jshubham.bookmyshow.models;

import com.jshubham.bookmyshow.models.enums.Feature;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Movie extends BaseModel{
    private String name;
    private Date releaseDate;
    private List<Feature> features;
}
