package com.jshubham.bookmyshow.models;

import com.jshubham.bookmyshow.models.enums.Feature;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private String description;

    @Enumerated(EnumType.ORDINAL)
    @ElementCollection // this will basically will create a mapping table between movie and list of enum.feature.id's
    private List<Feature> features;
}
