package com.coworkingspace.server.repositories;

import com.coworkingspace.server.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByRoomIdAndDate(Long roomId, LocalDate date);

    List<Booking> findAllByFreelancerIdOrInternId(Long freelancerId, Long internId);

    long countByRoomId(Long roomId);

    long countByDate(LocalDate date);

    List<Booking> findByDate(LocalDate date);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.room.id = :roomId AND b.freelancer IS NOT NULL")
    long countFreelancersByRoomId(@Param("roomId") Long roomId);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.room.id = :roomId AND b.intern IS NOT NULL")
    long countInternsByRoomId(@Param("roomId") Long roomId);

    List<Booking> findByDateAfter(LocalDate start);

    List<Booking> findByDateBetween(LocalDate start, LocalDate end);
    @Query(value = "SELECT * FROM bookings b WHERE " +
            "CAST(CONCAT(b.date, ' ', b.start_time) AS TIMESTAMP) < :endDateTime AND " +
            "CAST(CONCAT(b.date, ' ', b.end_time) AS TIMESTAMP) > :startDateTime",
            nativeQuery = true)
    List<Booking> findBookingsOverlappingDateRange(@Param("startDateTime") LocalDateTime startDateTime,
                                                   @Param("endDateTime") LocalDateTime endDateTime);
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.room.id = :roomId AND b.date = :date AND :time BETWEEN b.startTime AND b.endTime")
    int countBookingsInRoomAtTime(@Param("roomId") Long roomId, @Param("date") LocalDate date, @Param("time") LocalTime time);


    @Query("SELECT b FROM Booking b WHERE b.date = :date AND " +
            "(b.startTime < :endTime AND b.endTime > :startTime)")
    List<Booking> findBookingsByDateAndTimeRange(@Param("date") LocalDate date,
                                                 @Param("startTime") LocalTime startTime,
                                                 @Param("endTime") LocalTime endTime);

}

