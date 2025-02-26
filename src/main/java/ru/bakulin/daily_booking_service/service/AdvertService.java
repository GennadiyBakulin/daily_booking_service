package ru.bakulin.daily_booking_service.service;

import ru.bakulin.daily_booking_service.dto.AdvertDtoRq;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRs;
import ru.bakulin.daily_booking_service.dto.AdvertPaginationDto;

public interface AdvertService {

  AdvertDtoRs save(AdvertDtoRq dto);

  AdvertPaginationDto getAdvertsForCity(String city, Integer pageNumber);
}
