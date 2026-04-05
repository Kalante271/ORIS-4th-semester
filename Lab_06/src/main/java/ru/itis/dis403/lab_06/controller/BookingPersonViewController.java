package ru.itis.dis403.lab_06.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itis.dis403.lab_06.dto.BookingPersonViewDto;
import ru.itis.dis403.lab_06.dto.BookingsViewResponse;
import ru.itis.dis403.lab_06.service.BookingPersonViewService;
import ru.itis.dis403.lab_06.service.UserDetailImpl;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingPersonViewController {

    private final BookingPersonViewService bookingPersonViewService;

    public BookingPersonViewController(BookingPersonViewService bookingPersonViewService) {
        this.bookingPersonViewService = bookingPersonViewService;
    }

    @GetMapping("/allview")
    public ResponseEntity<BookingsViewResponse> getBookings() {

        UserDetailImpl userDetails =
                (UserDetailImpl) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        System.out.println(userDetails.getUser());
        List<BookingPersonViewDto> bookings = bookingPersonViewService.findByHotelId(userDetails.getUser().getHotel().getId());

        bookings.forEach(b-> System.out.println(b.getId()));

        return ResponseEntity.ok(new BookingsViewResponse(bookings));
    }
}