package ru.bakulin.daily_booking_service.service;

import ru.bakulin.daily_booking_service.dto.AdvertDtoRq;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRs;
import ru.bakulin.daily_booking_service.dto.PageDto;

public interface AdvertService {

  AdvertDtoRs save(AdvertDtoRq dto);

  PageDto<AdvertDtoRs> getAdvertsForCity(String city, Integer pageNumber);
}
