package ru.bakulin.daily_booking_service.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bakulin.daily_booking_service.dto.BookingDtoRq;
import ru.bakulin.daily_booking_service.dto.BookingDtoRs;
import ru.bakulin.daily_booking_service.dto.ClientDto;
import ru.bakulin.daily_booking_service.dto.PageDto;
import ru.bakulin.daily_booking_service.entity.Advert;
import ru.bakulin.daily_booking_service.entity.Booking;
import ru.bakulin.daily_booking_service.exception.EntityNotFound;
import ru.bakulin.daily_booking_service.exception.UnavailableBookingPeriod;
import ru.bakulin.daily_booking_service.mapper.BookingMapper;
import ru.bakulin.daily_booking_service.repository.AdvertRepository;
import ru.bakulin.daily_booking_service.repository.BookingRepository;
import ru.bakulin.daily_booking_service.repository.ClientRepository;
import ru.bakulin.daily_booking_service.service.BookingService;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

  private static final Integer PAGE_SIZE = 20;

  private final AdvertRepository advertRepository;
  private final ClientServiceImpl clientService;
  private final ClientRepository clientRepository;
  private final BookingRepository repository;
  private final BookingMapper mapper;

  @Transactional
  @Override
  public BookingDtoRs save(BookingDtoRq dtoRq) {
    ClientDto client = dtoRq.getClient();

    if (Objects.nonNull(client.getId()) && !clientRepository.existsById(client.getId())) {
      throw new EntityNotFound(
          "Клиент с указанным Id= %s не найден в БД".formatted(client.getId()));
    }

    if (Objects.isNull(client.getId())) {
      dtoRq.setClient(clientService.save(client));
    }

    BigDecimal resultPrice = getResultPrice(dtoRq);

    checkFreeBookingPeriod(dtoRq);

    Booking entity = mapper.toEntityWithRelation(dtoRq);
    entity.setAmount(resultPrice);

    Booking booking = repository.save(entity);

    return mapper.toDtoRs(booking);
  }

  protected BigDecimal getResultPrice(BookingDtoRq dto) {
    Advert advert = advertRepository.findById(dto.getAdvertId()).orElseThrow(
        () -> new EntityNotFound(
            "Не найдено Объявление с указанным Id= %s".formatted(dto.getAdvertId())));

    LocalDate start = dto.getDateStart();
    LocalDate finish = dto.getDateFinish();

    checkCorrectInputPeriodBooking(start, finish);
    long countDayBooking = ChronoUnit.DAYS.between(start, finish);

    return advert.getPrice().multiply(BigDecimal.valueOf(countDayBooking));
  }

  @Override
  public PageDto<BookingDtoRs> findAllByClientEmail(String email, Integer pageNumber) {
    if (Objects.isNull(pageNumber)) {
      pageNumber = 0;
    }
    PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE);
    Page<Booking> page = repository.findAllByClientEmail(email, pageRequest);

    return mapper.toPageDto(page);
  }

  private void checkCorrectInputPeriodBooking(LocalDate start, LocalDate finish) {
    if (start.isAfter(finish)) {
      throw new UnavailableBookingPeriod(
          "Ошибка в указании периода бронирования. Дата окончания бронирования ранее даты начала бронирования.");
    }
  }

  private void checkFreeBookingPeriod(BookingDtoRq dtoRq) {
    LocalDate startDate = dtoRq.getDateStart();
    LocalDate finishDate = dtoRq.getDateFinish();
    List<Booking> bookings = repository.findAllForCurrentApartment(dtoRq.getAdvertId());

    for (Booking booking : bookings) {
      if (!finishDate.isBefore(booking.getDateStart())
          && !startDate.isAfter(booking.getDateFinish())) {
        throw new UnavailableBookingPeriod("Бронирование помещения на данный период не доступно.");
      }
    }
  }
}
