package com.jshubham.bookmyshow.repositories;

import com.jshubham.bookmyshow.models.Movie;
import com.jshubham.bookmyshow.models.Rating;
import com.jshubham.bookmyshow.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
    Optional<List<Rating>> findByMovie(Movie movie);

    Optional<Rating> findByUserAndMovie(User user, Movie movie);
}
