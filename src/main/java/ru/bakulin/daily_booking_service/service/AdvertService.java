package ru.bakulin.daily_booking_service.service;

import ru.bakulin.daily_booking_service.dto.AdvertDtoRq;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRs;

public interface AdvertService {

  AdvertDtoRs save(AdvertDtoRq dto);
}
