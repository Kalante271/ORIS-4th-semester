package ru.itis.dis403.lab_06.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.dis403.lab_06.model.BookingPersonView;
import java.util.List;

public interface BookingPersonViewRepository extends JpaRepository<BookingPersonView, Long> {
    List<BookingPersonView> findByHotelId(Long hotelId);
}
