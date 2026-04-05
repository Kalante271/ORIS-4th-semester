package ru.itis.dis403.lab_06.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.dis403.lab_06.model.Booking;
import ru.itis.dis403.lab_06.model.Hotel;


import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByHotel(Hotel hotel);

    @Query("select b from Booking b where b.id = :id and hotel.id = :hotelId ")
    Booking findByIdAndHotelId(@Param("id") Long id, @Param("hotelId") Long hotelId);
}
