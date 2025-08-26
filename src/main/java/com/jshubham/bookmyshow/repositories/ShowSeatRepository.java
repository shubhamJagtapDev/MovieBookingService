package com.jshubham.bookmyshow.repositories;

import com.jshubham.bookmyshow.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    Optional<List<ShowSeat>> findByShowId(Long showId);
    List<ShowSeat> findAllById(List<Long> id);

    ShowSeat findById(Long id);
}
