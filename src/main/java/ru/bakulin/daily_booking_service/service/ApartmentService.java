package ru.bakulin.daily_booking_service.service;

import org.springframework.stereotype.Service;
import ru.bakulin.daily_booking_service.dto.ApartmentDto;
import ru.bakulin.daily_booking_service.entity.Apartment;
import ru.bakulin.daily_booking_service.mapper.ApartmentMapper;
import ru.bakulin.daily_booking_service.repository.ApartmentRepository;

@Service
public class ApartmentService {

  ApartmentMapper mapper;
  ApartmentRepository repository;

  public ApartmentDto save(ApartmentDto dto) {
    Apartment entity = mapper.toEntity(dto);
    Apartment apartment = repository.save(entity);
    return mapper.toDto(apartment);
  }
}
