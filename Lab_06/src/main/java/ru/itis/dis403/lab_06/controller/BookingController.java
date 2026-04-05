package ru.itis.dis403.lab_06.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itis.dis403.lab_06.dto.BookingDto;
import ru.itis.dis403.lab_06.dto.BookingsResponse;
import ru.itis.dis403.lab_06.model.Booking;
import ru.itis.dis403.lab_06.repository.BookingRepository;
import ru.itis.dis403.lab_06.service.BookingService;
import ru.itis.dis403.lab_06.service.UserDetailImpl;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final BookingService bookingService;

    public BookingController(BookingRepository bookingRepository, BookingService bookingService) {
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable("id") Long id) {

        UserDetailImpl userDetails =
                (UserDetailImpl) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        System.out.println(userDetails.getUser());
        BookingDto booking = bookingService.getBookingById(id, userDetails.getUser());

        return ResponseEntity.ok(booking);
    }

    @GetMapping("/all")
    public ResponseEntity<BookingsResponse> getBookings() {

        UserDetailImpl userDetails =
                (UserDetailImpl) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        System.out.println(userDetails.getUser());
        List<Booking> bookings = bookingRepository.findByHotel(userDetails.getUser().getHotel());

        bookings.forEach(b-> System.out.println(b.getId()));

        return ResponseEntity.ok(new BookingsResponse(bookings));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateBooking(@RequestBody BookingDto bookingDto) {

        UserDetailImpl userDetails =
                (UserDetailImpl) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        bookingService.updateBooking(bookingDto, userDetails.getUser());

        return ResponseEntity.ok().build();
    }
}