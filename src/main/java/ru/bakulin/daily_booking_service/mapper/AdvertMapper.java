package ru.bakulin.daily_booking_service.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRq;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRs;
import ru.bakulin.daily_booking_service.dto.AdvertPaginationDto;
import ru.bakulin.daily_booking_service.entity.Advert;
import ru.bakulin.daily_booking_service.entity.Apartment;
import ru.bakulin.daily_booking_service.repository.ApartmentRepository;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AdvertMapper {

  @Autowired
  private ApartmentRepository apartmentRepository;

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "bookings", ignore = true)
  @Mapping(target = "apartment", source = "apartmentId", qualifiedByName = "getApartmentById")
  public abstract Advert toEntityWithRelation(AdvertDtoRq dto);

  @Named("getApartmentById")
  protected Apartment getApartmentById(Integer id) {
    return apartmentRepository.findById(id).orElseThrow();
  }

  public abstract AdvertDtoRs toDtoRs(Advert advert);

  @Mapping(target = "totalPages", source = "page", qualifiedByName = "getTotalPages")
  @Mapping(target = "totalElements", source = "page", qualifiedByName = "getTotalElements")
  @Mapping(target = "numberPage", source = "page", qualifiedByName = "getNumberPage")
  @Mapping(target = "adverts", source = "page", qualifiedByName = "getContent")
  public abstract AdvertPaginationDto toPaginationDto(Page<Advert> page);

  @Named("getTotalPages")
  protected int getTotalPages(Page<Advert> page) {
    return page.getTotalPages();
  }

  @Named("getTotalElements")
  protected long getTotalElements(Page<Advert> page) {
    return page.getTotalElements();
  }

  @Named("getNumberPage")
  protected int getNumberPage(Page<Advert> page) {
    return page.getNumber();
  }

  @Named("getContent")
  protected List<AdvertDtoRs> getContent(Page<Advert> page) {
    List<Advert> adverts = page.getContent();
    return adverts.stream().map(this::toDtoRs).toList();
  }
}
