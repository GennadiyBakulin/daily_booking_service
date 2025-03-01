package ru.bakulin.daily_booking_service.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bakulin.daily_booking_service.dto.BookingDtoRq;
import ru.bakulin.daily_booking_service.dto.BookingDtoRs;
import ru.bakulin.daily_booking_service.dto.BookingPaginationDto;
import ru.bakulin.daily_booking_service.dto.ClientDto;
import ru.bakulin.daily_booking_service.entity.Advert;
import ru.bakulin.daily_booking_service.entity.Apartment;
import ru.bakulin.daily_booking_service.entity.Booking;
import ru.bakulin.daily_booking_service.mapper.BookingMapper;
import ru.bakulin.daily_booking_service.repository.AdvertRepository;
import ru.bakulin.daily_booking_service.repository.BookingRepository;
import ru.bakulin.daily_booking_service.repository.ClientRepository;
import ru.bakulin.daily_booking_service.service.BookingService;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

  private static final Integer PAGE_SIZE = 20;

  private final ClientServiceImpl clientService;
  private final ClientRepository clientRepository;
  private final AdvertRepository advertRepository;
  private final BookingRepository repository;
  private final BookingMapper mapper;

  @Override
  @Transactional
  public BookingDtoRs save(BookingDtoRq dtoRq) {
    ClientDto client = dtoRq.getClient();

    if (Objects.nonNull(client.getId()) && !clientRepository.existsById(client.getId())) {
      throw new RuntimeException();
    }
    if (Objects.isNull(client.getId())) {
      client = clientService.save(client);
    }

    Advert currentAdvert = advertRepository.findById(dtoRq.getAdvertId()).orElseThrow();
    Apartment apartment = currentAdvert.getApartment();
    List<Advert> adverts = apartment.getAdverts();
    List<Booking> bookings = adverts.stream()
        .flatMap(advert -> advert.getBookings().stream())
        .toList();

    LocalDate startDateBooking = dtoRq.getDateStart();
    LocalDate finishDateBooking = dtoRq.getDateFinish();
    for (Booking booking : bookings) {
      if (finishDateBooking.isAfter(booking.getDateStart())
          && startDateBooking.isBefore(booking.getDateFinish())) {
        throw new RuntimeException();
      }
    }

    Booking entity = mapper.toEntityWithRelation(dtoRq);
    Booking booking = repository.save(entity);

    return mapper.toDtoRs(booking);
  }

  @Override
  public BookingPaginationDto getBookingsForClientByEmail(String email, Integer pageNumber) {
    if (Objects.isNull(pageNumber)) {
      pageNumber = 0;
    }
    PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE);
    Page<Booking> page = repository.findAllByClientEmail(email, pageRequest);

    return mapper.toPaginationDto(page);
  }
}
