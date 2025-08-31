package com.jshubham.bookmyshow.repositories;

import com.jshubham.bookmyshow.models.ShowSeat;
import com.jshubham.bookmyshow.models.enums.ShowSeatStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    Optional<List<ShowSeat>> findByShowId(Long showId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ShowSeat> findById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE) // equivalent to select .. for update lock in db
    @Query("SELECT ss FROM ShowSeat ss WHERE ss.id IN :ids AND ss.status = :status")
    List<ShowSeat> findAllByIdAndShowSeatStatus(@Param("ids") List<Long> ids, @Param("status") ShowSeatStatus status);
}
