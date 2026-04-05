package ru.itis.dis403.lab_06.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dis403.lab_06.dto.BookingDto;
import ru.itis.dis403.lab_06.model.Booking;
import ru.itis.dis403.lab_06.model.Person;
import ru.itis.dis403.lab_06.model.User;
import ru.itis.dis403.lab_06.repository.BookingRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public BookingDto getBookingById(Long bookingId, User user) {
        Booking b = bookingRepository.findByIdAndHotelId(bookingId, user.getHotel().getId());

        return BookingDto.builder()
                .id(b.getId())
                .arrivaldate(b.getArrivaldate())
                .stayingdate(b.getStayingdate())
                .departuredate(b.getDeparturedate())
                .personId(b.getPerson().getId())
                .name(b.getPerson().getName())
                .gender(b.getPerson().getGender())
                .birthDate(b.getPerson().getBirthdate())
                .build();
    }

    @Transactional
    public void updateBooking(BookingDto bookingDto, User user) {
        Booking b = bookingRepository.findByIdAndHotelId(bookingDto.getId(), user.getHotel().getId());

        b.setArrivaldate(bookingDto.getArrivaldate());
        b.setStayingdate(bookingDto.getStayingdate());
        b.setDeparturedate(bookingDto.getDeparturedate());

        Person person = b.getPerson();
        if (person != null) {
            person.setName(bookingDto.getName());
            person.setGender(bookingDto.getGender());
            person.setBirthdate(bookingDto.getBirthDate());
        }

        bookingRepository.save(b);
    }
}
