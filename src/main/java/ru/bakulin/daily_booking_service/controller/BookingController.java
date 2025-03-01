package ru.bakulin.daily_booking_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bakulin.daily_booking_service.dto.BookingDtoRq;
import ru.bakulin.daily_booking_service.dto.BookingDtoRs;
import ru.bakulin.daily_booking_service.dto.BookingPaginationDto;
import ru.bakulin.daily_booking_service.service.BookingService;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

  private final BookingService service;

  @PostMapping
  public BookingDtoRs create(@RequestBody BookingDtoRq dto) {
    return service.save(dto);
  }

  @GetMapping
  public BookingPaginationDto getBookingsForClientByEmail(
      @RequestParam String email,
      @RequestParam(required = false) Integer page) {

    return service.getBookingsForClientByEmail(email, page);
  }
}
