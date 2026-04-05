package ru.itis.dis403.lab_06.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.dis403.lab_06.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
