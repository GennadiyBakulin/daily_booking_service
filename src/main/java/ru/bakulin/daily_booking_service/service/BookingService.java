package ru.bakulin.daily_booking_service.service;

import ru.bakulin.daily_booking_service.dto.BookingDtoRq;
import ru.bakulin.daily_booking_service.dto.BookingDtoRs;
import ru.bakulin.daily_booking_service.dto.BookingPaginationDto;

public interface BookingService {

  BookingDtoRs save(BookingDtoRq dtoRq);

  BookingPaginationDto getBookingsForClientByEmail(String email, Integer pageNumber);
}
