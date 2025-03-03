package ru.bakulin.daily_booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRq;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRs;
import ru.bakulin.daily_booking_service.dto.PageDto;
import ru.bakulin.daily_booking_service.entity.Advert;
import ru.bakulin.daily_booking_service.entity.Apartment;
import ru.bakulin.daily_booking_service.exception.EntityNotFound;
import ru.bakulin.daily_booking_service.repository.ApartmentRepository;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AdvertMapper {

  @Autowired
  private ApartmentRepository apartmentRepository;

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "bookings", ignore = true)
  @Mapping(target = "apartment", source = "apartmentId", qualifiedByName = "getApartmentById")
  public abstract Advert toEntityWithRelation(AdvertDtoRq dto);

  public abstract AdvertDtoRs toDtoRs(Advert advert);

  public abstract PageDto<AdvertDtoRs> toPageDto(Page<Advert> page);

  @Named("getApartmentById")
  protected Apartment getApartmentById(Integer id) {
    return apartmentRepository.findById(id).orElseThrow(
        () -> new EntityNotFound("Помещение с указанным Id= %s не найдено в БД".formatted(id)));
  }
}
