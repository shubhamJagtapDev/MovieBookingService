package com.jshubham.bookmyshow.repositories;

import com.jshubham.bookmyshow.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // SELECT * FROM User WHERE user_id = id
    Optional<User> findById(Long id);
}
