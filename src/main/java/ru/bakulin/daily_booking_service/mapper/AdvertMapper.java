package ru.bakulin.daily_booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRq;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRs;
import ru.bakulin.daily_booking_service.entity.Advert;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdvertMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "apartment", ignore = true)
  Advert toEntity(AdvertDtoRq dto);

  AdvertDtoRs toDtoRs(Advert advert);
}
