package ru.bakulin.daily_booking_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRq;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRs;
import ru.bakulin.daily_booking_service.entity.Advert;
import ru.bakulin.daily_booking_service.mapper.AdvertMapper;
import ru.bakulin.daily_booking_service.repository.AdvertRepository;
import ru.bakulin.daily_booking_service.service.AdvertService;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {

  private final AdvertRepository repository;
  private final AdvertMapper mapper;

  @Override
  public AdvertDtoRs save(AdvertDtoRq dto) {
    Advert advertEntity = mapper.toEntityWithRelation(dto);

    Advert advert = repository.save(advertEntity);

    return mapper.toDtoRs(advert);
  }
}
