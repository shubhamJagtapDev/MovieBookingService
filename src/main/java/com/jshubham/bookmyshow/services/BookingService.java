package com.jshubham.bookmyshow.services;

import com.jshubham.bookmyshow.exceptions.*;
import com.jshubham.bookmyshow.models.*;
import com.jshubham.bookmyshow.models.enums.BookingStatus;
import com.jshubham.bookmyshow.models.enums.ShowSeatStatus;
import com.jshubham.bookmyshow.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BookingService {
    private final ShowRepository showRepository;
    private final UserRepository userRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ShowSeatTypeRepository showSeatTypeRepository;
    private final BookingRepository bookingRepository;
    private static int bookingCnt = 0;


    @Autowired
    public BookingService(ShowRepository showRepository, UserRepository userRepository,
                          ShowSeatRepository showSeatRepository, ShowSeatTypeRepository showSeatTypeRepository,
                          BookingRepository bookingRepository) {
        this.showRepository = showRepository;
        this.userRepository = userRepository;
        this.showSeatRepository = showSeatRepository;
        this.showSeatTypeRepository = showSeatTypeRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking createBooking(Long userId, List<Long> showSeatIds, Long showId) throws UserNotFoundException,
            ShowNotFoundException, ShowSeatNotFoundException {
        // Get the user with the given userId and validate.
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new UserNotFoundException("User with id: " + userId + " not found");
        User user = optionalUser.get();

        // Get the show with the given showId and validate.
        Optional<Show> optionalShow = showRepository.findById(showId);
        if (optionalShow.isEmpty()) throw new ShowNotFoundException("Show with id: " + showId + " not found");
        Show show = optionalShow.get();

        // Get the list of show seats for the give show seat ids
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);
        if (showSeats.size() != showSeatIds.size())
            throw new ShowSeatNotFoundException("Seats for show with id: " + showId + " not found");

        // Change the show seat status from AVAILABLE to BLOCKED
        List<ShowSeat> showSeatsBlocked;
        for (ShowSeat showSeat : showSeats) {
            if (!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAIlABLE)) {
                throw new ShowSeatUnAvailableForBookingException("Seats for show with id: " + showSeat.getId()
                        + " not available. Current status of show seat : " + showSeat.getShowSeatStatus());
            }
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            showSeat.setLastModifiedAt(new Date());
        }
        showSeatsBlocked = showSeatRepository.saveAll(showSeats);

        // Calculating the price for the booking
        int totalAmount = 0;
        Optional<List<ShowSeatType>> showSeatTypesOptional = showSeatTypeRepository.findByShow(show);
        if (showSeatTypesOptional.isEmpty()) throw new ShowSeatTypeNotFoundException("For show with id: "
                + showId + " seat type prices are not set/found");
        List<ShowSeatType> showSeatTypes = showSeatTypesOptional.get();

        for (ShowSeat bookedSeat : showSeatsBlocked) {
            for (ShowSeatType bookedSeatType : showSeatTypes) {
                if (bookedSeat.getSeat().getSeatType().equals(bookedSeatType.getSeatType())) {
                    totalAmount += bookedSeatType.getPrice();
                }
            }
        }

        // Building the Booking object
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setShowSeats(showSeatsBlocked);
        booking.setAmount(totalAmount);
        booking.setBookingStatus(BookingStatus.PENDING);
        bookingCnt++;
        booking.setBookingNumber("BMS" + bookingCnt);
        booking.setCreatedAt(new Date());

        return bookingRepository.save(booking);
    }
}
