package ru.bakulin.daily_booking_service.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import ru.bakulin.daily_booking_service.dto.BookingDtoRq;
import ru.bakulin.daily_booking_service.dto.BookingDtoRs;
import ru.bakulin.daily_booking_service.dto.PaginationDto;
import ru.bakulin.daily_booking_service.entity.Advert;
import ru.bakulin.daily_booking_service.entity.Booking;
import ru.bakulin.daily_booking_service.exception.NotFound;
import ru.bakulin.daily_booking_service.exception.UnavailableBookingPeriod;
import ru.bakulin.daily_booking_service.repository.AdvertRepository;
import ru.bakulin.daily_booking_service.repository.ClientRepository;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class BookingMapper {

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private AdvertRepository advertRepository;

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "client.bookings", ignore = true)
  @Mapping(target = "advert", source = "advertId", qualifiedByName = "getAdvertById")
  @Mapping(target = "amount", source = "dto", qualifiedByName = "getResultPrice")
  public abstract Booking toEntityWithRelation(BookingDtoRq dto);

  @Mapping(source = "amount", target = "resultPrice")
  public abstract BookingDtoRs toDtoRs(Booking booking);

  @Named("getAdvertById")
  protected Advert getAdvertById(Integer id) {
    return advertRepository.findById(id).orElseThrow(
        () -> new NotFound("Объявление с указанным Id= %s не найдено в БД".formatted(id))
    );
  }

  @Named("getResultPrice")
  protected BigDecimal getResultPrice(BookingDtoRq dto) {
    Advert advert = getAdvertById(dto.getAdvertId());

    LocalDate start = dto.getDateStart();
    LocalDate finish = dto.getDateFinish();

    checkCorrectInputPeriodBooking(start, finish);
    long countDayBooking = ChronoUnit.DAYS.between(start, finish);

    return advert.getPrice().multiply(BigDecimal.valueOf(countDayBooking));
  }

  private void checkCorrectInputPeriodBooking(LocalDate start, LocalDate finish) {
    if (start.isAfter(finish)) {
      throw new UnavailableBookingPeriod(
          "Ошибка в указании периода бронирования. Дата окончания бронирования ранее даты начала бронирования.");
    }
  }

  @Mapping(target = "totalPages", source = "page", qualifiedByName = "getTotalPages")
  @Mapping(target = "totalElements", source = "page", qualifiedByName = "getTotalElements")
  @Mapping(target = "numberPage", source = "page", qualifiedByName = "getNumberPage")
  @Mapping(target = "content", source = "page", qualifiedByName = "getContent")
  public abstract PaginationDto<BookingDtoRs> toPaginationDto(Page<Booking> page);

  @Named("getTotalPages")
  protected int getTotalPages(Page<Booking> page) {
    return page.getTotalPages();
  }

  @Named("getTotalElements")
  protected long getTotalElements(Page<Booking> page) {
    return page.getTotalElements();
  }

  @Named("getNumberPage")
  protected int getNumberPage(Page<Booking> page) {
    return page.getNumber();
  }

  @Named("getContent")
  protected List<BookingDtoRs> getContent(Page<Booking> page) {
    List<Booking> bookings = page.getContent();
    return bookings.stream().map(this::toDtoRs).toList();
  }
}
