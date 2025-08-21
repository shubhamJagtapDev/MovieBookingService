package com.jshubham.bookmyshow.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {
    @Id // PK attr for all the child classes of BaseModel
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO INCREMENT
    private Long id;
    private Date createdAt;
    private Date lastModifiedAt;
}
// MappedSuperclass will propagate all the attributes of the BaseModel to all the child classes
// JPA won't create the table for this class
// It will add this attributes to the tables of the child classes