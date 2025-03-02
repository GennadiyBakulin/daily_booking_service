package ru.bakulin.daily_booking_service.service;

import ru.bakulin.daily_booking_service.dto.BookingDtoRq;
import ru.bakulin.daily_booking_service.dto.BookingDtoRs;
import ru.bakulin.daily_booking_service.dto.PaginationDto;

public interface BookingService {

  BookingDtoRs save(BookingDtoRq dtoRq);

  PaginationDto<BookingDtoRs> getBookingsForClientByEmail(String email, Integer pageNumber);
}
