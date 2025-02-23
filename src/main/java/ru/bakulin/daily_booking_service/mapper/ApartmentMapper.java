package ru.bakulin.daily_booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bakulin.daily_booking_service.dto.ApartmentDto;
import ru.bakulin.daily_booking_service.entity.Apartment;

@Mapper
public interface ApartmentMapper {

  @Mapping(target = "id", ignore = true)
  Apartment toEntity(ApartmentDto apartmentDto);

  ApartmentDto toDto(Apartment apartment);
}
