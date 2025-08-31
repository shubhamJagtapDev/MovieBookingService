package com.jshubham.bookmyshow.models;

import com.jshubham.bookmyshow.models.enums.UserType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
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

    // mappedBy tells spring that don't create a mapping table instead create a foreign key of user_id
    // mapped by "bookedBy" in the bookings table
    // We can use mapped by when we have bidirectional connection
    @OneToMany(mappedBy = "bookedBy")
    List<Booking> bookings;
    // JoinColumn(name="bookedBy_id")
    // this is for the case when we connection only one table say in Bookings we
    // can tell to spring to create a foreign key(having name bookedBy_id) in bookings table

    @Enumerated(EnumType.ORDINAL)
    private UserType userType;
}
