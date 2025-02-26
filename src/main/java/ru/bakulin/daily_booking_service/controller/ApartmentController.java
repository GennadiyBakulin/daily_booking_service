package ru.bakulin.daily_booking_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bakulin.daily_booking_service.dto.ApartmentDto;
import ru.bakulin.daily_booking_service.service.ApartmentService;

@RestController
@RequestMapping("/apartment")
@RequiredArgsConstructor
public class ApartmentController {

  private final ApartmentService service;

  @PostMapping
  public ApartmentDto create(@RequestBody ApartmentDto dto) {
    return service.save(dto);
  }
}
