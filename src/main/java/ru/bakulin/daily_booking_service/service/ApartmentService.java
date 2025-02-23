package ru.bakulin.daily_booking_service.service;

import ru.bakulin.daily_booking_service.dto.ApartmentDto;
import ru.bakulin.daily_booking_service.entity.Apartment;

public interface ApartmentService {

  ApartmentDto save(ApartmentDto dto);

  Apartment findApartmentById(Integer id);
}
