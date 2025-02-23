package ru.bakulin.daily_booking_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bakulin.daily_booking_service.dto.ApartmentDto;
import ru.bakulin.daily_booking_service.entity.Apartment;
import ru.bakulin.daily_booking_service.mapper.ApartmentMapper;
import ru.bakulin.daily_booking_service.repository.ApartmentRepository;
import ru.bakulin.daily_booking_service.service.ApartmentService;

@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {

  private final ApartmentRepository repository;
  private final ApartmentMapper mapper;

  @Override
  public ApartmentDto save(ApartmentDto dto) {
    Apartment entity = mapper.toEntity(dto);
    Apartment apartment = repository.save(entity);

    return mapper.toDto(apartment);
  }

  @Override
  public Apartment findApartmentById(Integer id) {
    return repository.findById(id).orElseThrow();
  }
}
