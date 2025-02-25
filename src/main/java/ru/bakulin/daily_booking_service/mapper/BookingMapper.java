package ru.bakulin.daily_booking_service.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bakulin.daily_booking_service.dto.BookingDtoRq;
import ru.bakulin.daily_booking_service.dto.BookingDtoRs;
import ru.bakulin.daily_booking_service.entity.Advert;
import ru.bakulin.daily_booking_service.entity.Booking;
import ru.bakulin.daily_booking_service.entity.Client;
import ru.bakulin.daily_booking_service.repository.AdvertRepository;
import ru.bakulin.daily_booking_service.repository.ClientRepository;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class BookingMapper {

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private AdvertRepository advertRepository;

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "client", source = "clientId", qualifiedByName = "getClientById")
  @Mapping(target = "advert", source = "advertId", qualifiedByName = "getAdvertById")
  @Mapping(target = "amount", qualifiedByName = "calculateResultPrice")
  public abstract Booking toEntityWithRelation(BookingDtoRq dto);

  @Mapping(source = "amount", target = "resultPrice")
  public abstract BookingDtoRs toDtoRs(Booking booking);

  @Named("getClientById")
  protected Client getClientById(Integer id) {
    return clientRepository.findById(id).orElseThrow();
  }

  @Named("getAdvertById")
  protected Advert getAdvertById(Integer id) {
    return advertRepository.findById(id).orElseThrow();
  }

  @Named("calculateResultPrice")
  protected BigDecimal calculateResultPrice(BookingDtoRq dto) {
    Advert advert = getAdvertById(dto.getAdvertId());

    LocalDate start = dto.getDateStart();
    LocalDate finish = dto.getDateFinish();
    long countDayBooking = ChronoUnit.DAYS.between(start, finish);

    if (countDayBooking < 0) {
      throw new RuntimeException(
          "Ошибка в указании периода бронирования. Дата окончания бронирования раньше даты начала бронирования.");
    }

    return advert.getPrice().multiply(BigDecimal.valueOf(countDayBooking));
  }
}
