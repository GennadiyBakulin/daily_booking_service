package ru.bakulin.daily_booking_service.service.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRq;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRs;
import ru.bakulin.daily_booking_service.dto.PageDto;
import ru.bakulin.daily_booking_service.entity.Advert;
import ru.bakulin.daily_booking_service.mapper.AdvertMapper;
import ru.bakulin.daily_booking_service.repository.AdvertRepository;
import ru.bakulin.daily_booking_service.service.AdvertService;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {

  private static final Integer PAGE_SIZE = 10;

  private final AdvertRepository repository;
  private final AdvertMapper mapper;

  @Override
  public AdvertDtoRs save(AdvertDtoRq dto) {
    Advert advertEntity = mapper.toEntityWithRelation(dto);

    Advert advert = repository.save(advertEntity);

    return mapper.toDtoRs(advert);
  }

  @Override
  public PageDto<AdvertDtoRs> findAllByCity(String city, Integer pageNumber) {
    if (Objects.isNull(pageNumber)) {
      pageNumber = 0;
    }
    PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE);
    Page<Advert> page = repository.findAllByApartmentCityOrderByPriceDesc(city, pageRequest);

    return mapper.toPageDto(page);
  }
}
