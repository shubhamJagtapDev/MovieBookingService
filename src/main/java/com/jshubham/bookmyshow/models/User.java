package com.jshubham.bookmyshow.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "users") // User is reserved keyword in MYSQL, so we need to change it her
public class User extends BaseModel{
    private String name;
    private String email;
    private String password;
    List<Booking> bookings;
}
