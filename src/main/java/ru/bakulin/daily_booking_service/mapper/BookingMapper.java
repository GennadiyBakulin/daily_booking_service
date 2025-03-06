package ru.bakulin.daily_booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import ru.bakulin.daily_booking_service.dto.BookingDtoRq;
import ru.bakulin.daily_booking_service.dto.BookingDtoRs;
import ru.bakulin.daily_booking_service.dto.PageDto;
import ru.bakulin.daily_booking_service.entity.Advert;
import ru.bakulin.daily_booking_service.entity.Booking;
import ru.bakulin.daily_booking_service.exception.EntityNotFound;
import ru.bakulin.daily_booking_service.repository.AdvertRepository;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class BookingMapper {

  @Autowired
  private AdvertRepository advertRepository;

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "client.bookings", ignore = true)
  @Mapping(target = "advert", source = "advertId", qualifiedByName = "getAdvertById")
  @Mapping(target = "resultPrice", ignore = true)
  public abstract Booking toEntityWithRelation(BookingDtoRq dto);

  @Mapping(source = "resultPrice", target = "resultPrice")
  public abstract BookingDtoRs toDtoRs(Booking booking);

  public abstract PageDto<BookingDtoRs> toPageDto(Page<Booking> page);

  @Named("getAdvertById")
  protected Advert getAdvertById(Integer id) {
    return advertRepository.findById(id).orElseThrow(
        () -> new EntityNotFound("Объявление с указанным Id= %s не найдено в БД".formatted(id))
    );
  }
}
