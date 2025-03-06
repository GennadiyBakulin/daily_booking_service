package ru.bakulin.daily_booking_service.service;

import ru.bakulin.daily_booking_service.dto.BookingDtoRq;
import ru.bakulin.daily_booking_service.dto.BookingDtoRs;
import ru.bakulin.daily_booking_service.dto.PageDto;

public interface BookingService {

  BookingDtoRs save(BookingDtoRq dtoRq);

  PageDto<BookingDtoRs> findAllByClientEmail(String email, Integer pageNumber);
}
