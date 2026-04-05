package ru.itis.dis403.lab_06.dto;

import lombok.*;

import java.util.Date;

@Builder@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class BookingDto {
    private Long id;
    private Date arrivaldate;
    private Date stayingdate;
    private Date departuredate;

    private Long personId;
    private String name;
    private String gender;
    private Date birthDate;
}