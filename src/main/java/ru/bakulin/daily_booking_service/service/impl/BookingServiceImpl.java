package ru.bakulin.daily_booking_service.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    Client client = clientService.findById(dtoRq.getClientId());
    Advert advert = advertService.findById(dtoRq.getAdvertId());
    BigDecimal price = advert.getPrice();

    LocalDate dateStart = LocalDate.parse(dtoRq.getDateStart());
    LocalDate dateFinish = LocalDate.parse(dtoRq.getDateFinish());
    long countDayBooking = dateFinish.toEpochDay() - dateStart.toEpochDay();
    BigDecimal resultPrice = price.multiply(BigDecimal.valueOf(countDayBooking));

    Booking entity = mapper.toEntity(dtoRq, resultPrice);
    entity.setClient(client);
    entity.setAdvert(advert);
    entity.setDateStart(dateStart);
    entity.setDateFinish(dateFinish);

    Booking booking = repository.save(entity);

    return mapper.toDtoRs(booking);
  }
}
