package ru.bakulin.daily_booking_service.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bakulin.daily_booking_service.dto.BookingDtoRq;
import ru.bakulin.daily_booking_service.dto.BookingDtoRs;
import ru.bakulin.daily_booking_service.entity.Advert;
import ru.bakulin.daily_booking_service.entity.Booking;
import ru.bakulin.daily_booking_service.entity.Client;
import ru.bakulin.daily_booking_service.mapper.BookingMapper;
import ru.bakulin.daily_booking_service.repository.BookingRepository;
import ru.bakulin.daily_booking_service.service.BookingService;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

  private final ClientServiceImpl clientService;
  private final AdvertServiceImpl advertService;
  private final BookingRepository repository;
  private final BookingMapper mapper;

  @Override
  public BookingDtoRs save(BookingDtoRq dtoRq) {
    Booking entity = toEntityWithRelation(dtoRq);
    Booking booking = repository.save(entity);

    return mapper.toDtoRs(booking);
  }

  private Booking toEntityWithRelation(BookingDtoRq dtoRq) {
    Client client = clientService.findById(dtoRq.getClientId());
    Advert advert = advertService.findById(dtoRq.getAdvertId());

    LocalDate dateStart = dtoRq.getDateStart();
    LocalDate dateFinish = dtoRq.getDateFinish();
    BigDecimal price = advert.getPrice();

    BigDecimal resultPrice = calculateResultPrice(dateStart, dateFinish, price);

    Booking entity = mapper.toEntity(dtoRq, resultPrice);
    entity.setClient(client);
    entity.setAdvert(advert);

    return entity;
  }

  private BigDecimal calculateResultPrice(LocalDate start, LocalDate finish, BigDecimal price) {
    long countDayBooking = ChronoUnit.DAYS.between(start, finish);

    if (countDayBooking < 0) {
      throw new RuntimeException(
          "Ошибка в указании периода бронирования. Дата окончания бронирования раньше чем дата начала бронирования.");
    }

    return price.multiply(BigDecimal.valueOf(countDayBooking));
  }
}
