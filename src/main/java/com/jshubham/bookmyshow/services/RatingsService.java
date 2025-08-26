package com.jshubham.bookmyshow.services;

import com.jshubham.bookmyshow.exceptions.MovieNotFoundException;
import com.jshubham.bookmyshow.exceptions.UserNotFoundException;
import com.jshubham.bookmyshow.models.Movie;
import com.jshubham.bookmyshow.models.Rating;
import com.jshubham.bookmyshow.models.User;
import com.jshubham.bookmyshow.repositories.MovieRepository;
import com.jshubham.bookmyshow.repositories.RatingRepository;
import com.jshubham.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingsService {
    private UserRepository userRepository;
    private MovieRepository movieRepository;
    private RatingRepository ratingRepository;

    @Autowired
    public RatingsService(UserRepository userRepository, MovieRepository movieRepository,
                              RatingRepository ratingRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    public double getAverageRating(int movieId) throws MovieNotFoundException {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(movieOptional.isEmpty()) throw new MovieNotFoundException("Movie with id: " + movieId + " not found");
        Movie movie = movieOptional.get();
        Optional<List<Rating>> ratingOptional = ratingRepository.findByMovie(movie);
        if(ratingOptional.isPresent()) {
            List<Rating> movieRatings = ratingOptional.get();
            int totalRatingsCnt = movieRatings.size();
            int totalRating = 0;
            for(Rating rating : movieRatings) {
                totalRating += rating.getRating();
            }
            return (double) totalRating/totalRatingsCnt;
        }
        return (double) 0.0;
    }

    public Rating rateMovie(Long userId, int movieId, int rating) throws UserNotFoundException, MovieNotFoundException {
        Optional<User> userOp = userRepository.findById(userId);
        if(userOp.isEmpty()) throw new UserNotFoundException("User with id: " + userId + " not found");
        User user = userOp.get();

        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(movieOptional.isEmpty()) throw new MovieNotFoundException("Movie with id: " + movieId + " not found");
        Movie movie = movieOptional.get();

        Optional<Rating> optionalRating = ratingRepository.findByUserAndMovie(user, movie);
        if(optionalRating.isPresent()) {
            Rating movieRating = optionalRating.get();
            movieRating.setRating(rating);
            return ratingRepository.save(movieRating);
        }

        Rating movieRating = new Rating();

        movieRating.setMovie(movie);
        movieRating.setRating(rating);
        movieRating.setUser(user);

        return ratingRepository.save(movieRating);
    }

}

