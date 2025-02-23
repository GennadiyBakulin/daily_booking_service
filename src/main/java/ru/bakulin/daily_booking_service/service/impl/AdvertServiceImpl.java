package ru.bakulin.daily_booking_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRq;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRs;
import ru.bakulin.daily_booking_service.entity.Advert;
import ru.bakulin.daily_booking_service.entity.Apartment;
import ru.bakulin.daily_booking_service.mapper.AdvertMapper;
import ru.bakulin.daily_booking_service.repository.AdvertRepository;
import ru.bakulin.daily_booking_service.service.AdvertService;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {

  private final ApartmentServiceImpl apartmentService;
  private final AdvertRepository repository;
  private final AdvertMapper mapper;

  @Override
  public AdvertDtoRs save(AdvertDtoRq dto) {
    Apartment apartment = apartmentService.findById(dto.getApartmentId());

    Advert advertEntity = mapper.toEntity(dto);
    advertEntity.setApartment(apartment);

    Advert advert = repository.save(advertEntity);

    return mapper.toDtoRs(advert);
  }

  public Advert findById(Integer id) {
    return repository.findById(id).orElseThrow();
  }
}
