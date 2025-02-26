package ru.bakulin.daily_booking_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRq;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRs;
import ru.bakulin.daily_booking_service.dto.AdvertPaginationDto;
import ru.bakulin.daily_booking_service.service.AdvertService;

@RestController
@RequestMapping("/advert")
@RequiredArgsConstructor
public class AdvertController {

  private final AdvertService service;

  @PostMapping
  public AdvertDtoRs create(@RequestBody AdvertDtoRq dto) {
    return service.save(dto);
  }

  @GetMapping
  public AdvertPaginationDto getAdvertForCity(
      @RequestParam String city,
      @RequestParam(required = false) Integer page) {

    return service.getAdvertsForCity(city, page);
  }
}
