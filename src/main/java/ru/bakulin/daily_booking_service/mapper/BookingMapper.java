package ru.bakulin.daily_booking_service.mapper;

import java.math.BigDecimal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.bakulin.daily_booking_service.dto.BookingDtoRq;
import ru.bakulin.daily_booking_service.dto.BookingDtoRs;
import ru.bakulin.daily_booking_service.entity.Booking;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "client", ignore = true)
  @Mapping(target = "advert", ignore = true)
  @Mapping(target = "dateStart", ignore = true)
  @Mapping(target = "dateFinish", ignore = true)
  Booking toEntity(BookingDtoRq dto, BigDecimal amount);

  @Mapping(source = "amount", target = "resultPrice")
  BookingDtoRs toDtoRs(Booking booking);
}
