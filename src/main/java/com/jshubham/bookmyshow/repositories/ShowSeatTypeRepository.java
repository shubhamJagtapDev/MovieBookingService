package com.jshubham.bookmyshow.repositories;

import com.jshubham.bookmyshow.models.Show;
import com.jshubham.bookmyshow.models.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType, Integer> {
    Optional<List<ShowSeatType>> findByShow(Show show);
}
