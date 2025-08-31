package com.jshubham.bookmyshow.services;

import com.jshubham.bookmyshow.exceptions.*;
import com.jshubham.bookmyshow.models.*;
import com.jshubham.bookmyshow.models.enums.Feature;
import com.jshubham.bookmyshow.models.enums.SeatType;
import com.jshubham.bookmyshow.models.enums.ShowSeatStatus;
import com.jshubham.bookmyshow.models.enums.UserType;
import com.jshubham.bookmyshow.repositories.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShowService {
    private UserRepository userRepository;
    private MovieRepository movieRepository;
    private ScreenRepository screenRepository;
    private ShowSeatTypeRepository showSeatTypeRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private SeatRepository seatsRepository;

    @Autowired
    public ShowService(UserRepository userRepository, MovieRepository movieRepository,
                           ScreenRepository screenRepository, ShowSeatTypeRepository seatTypeShowRepository,
                           ShowRepository showRepository, SeatRepository seatsRepository,
                           ShowSeatRepository showSeatRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
        this.showSeatTypeRepository = seatTypeShowRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.seatsRepository = seatsRepository;
    }

    @Transactional
    public Show createShow(Long userId, Long movieId, Long screenId, Date startTime, Date endTime,
                           List<Pair<SeatType, Double>> pricingConfig, List<Feature> features)
            throws MovieNotFoundException, ScreenNotFoundException, FeatureNotSupportedByScreen, InvalidDateException,
            UserNotFoundException, UnAuthorizedAccessException {
        // Check for User exists
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) throw new UserNotFoundException(getEntityNotFoundExceptionMessage("User", userId));
        User user = optionalUser.get();

        // Authorized User check
        if(!user.getUserType().equals(UserType.ADMIN))
            throw new UnAuthorizedAccessException("User with id: " + " and type " + user.getUserType().name() + " unauthorised to add show details");

        // Check for Movie exists
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        if(optionalMovie.isEmpty()) throw new MovieNotFoundException(getEntityNotFoundExceptionMessage("Movie", movieId));
        Movie movie = optionalMovie.get();

        // Check for Screen exists
        Optional<Screen> optScreen = screenRepository.findById(screenId);
        if(optScreen.isEmpty()) throw new ScreenNotFoundException(getEntityNotFoundExceptionMessage("Screen", screenId));
        Screen screen = optScreen.get();

        // Initialize the lazy-loaded collection
        Hibernate.initialize(screen.getFeatures());

        // Check for valid show period
        if(startTime.getTime()>=endTime.getTime() || startTime.before(new Date(System.currentTimeMillis()))) throw new InvalidDateException("Show start time: " + startTime.toString() + " is greater than show end time: " + endTime.toString());

        // Validate features
        List<Feature> screenSupportedFeatures = screen.getFeatures();
        if (screenSupportedFeatures == null || screenSupportedFeatures.isEmpty()) {
            throw new FeatureNotSupportedByScreen("Screen does not support any features.");
        }
        for (Feature feature : features) {
            if (!screenSupportedFeatures.contains(feature)) {
                throw new FeatureNotSupportedByScreen("Screen does not support the feature: " + feature);
            }
        }

        Show show = new Show();
        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(startTime);
        show.setEndTime(endTime);
        show.setFeatures(features);
        show = showRepository.save(show);

        // 7. Create and save SeatTypeShow
        for (Pair<SeatType, Double> pair : pricingConfig) {
            ShowSeatType showSeatType = new ShowSeatType();
            showSeatType.setShow(show);
            showSeatType.setSeatType(pair.getFirst());
            showSeatType.setPrice(pair.getSecond());
            showSeatTypeRepository.save(showSeatType);
        }

        // 8. Create and save ShowSeat
        List<Seat> seats = seatsRepository.findAll().stream()
                .filter(seat -> seat.getScreen().getId() == screenId)
                .collect(Collectors.toList());

        for (Seat seat : seats) {
            ShowSeat showSeat = new ShowSeat();
            showSeat.setShow(show);
            showSeat.setSeat(seat);
            showSeat.setStatus(ShowSeatStatus.AVAILABLE);
            showSeatRepository.save(showSeat);
        }

        return show;
    }

    private String getEntityNotFoundExceptionMessage(String entityName, Long entityId) {
        return entityName + " with id: " + entityId + " not found";
    }
}
