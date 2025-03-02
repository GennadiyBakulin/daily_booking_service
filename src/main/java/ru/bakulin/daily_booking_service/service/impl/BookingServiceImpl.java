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
import ru.bakulin.daily_booking_service.dto.ClientDto;
import ru.bakulin.daily_booking_service.dto.PaginationDto;
import ru.bakulin.daily_booking_service.entity.Advert;
import ru.bakulin.daily_booking_service.entity.Apartment;
import ru.bakulin.daily_booking_service.entity.Booking;
import ru.bakulin.daily_booking_service.exception.NotFound;
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

  @Override
  @Transactional
  public BookingDtoRs save(BookingDtoRq dtoRq) {
    ClientDto client = dtoRq.getClient();

    if (Objects.nonNull(client.getId()) && !clientRepository.existsById(client.getId())) {
      throw new NotFound("Клиент с указанным Id= %s не найден в БД".formatted(client.getId()));
    }

    if (Objects.isNull(client.getId())) {
      dtoRq.setClient(clientService.save(client));
    }

    Booking entity = mapper.toEntityWithRelation(dtoRq);

    Advert advert = advertRepository.findById(dtoRq.getAdvertId()).orElseThrow();
    Apartment apartment = advert.getApartment();
    List<Advert> adverts = apartment.getAdverts();
    List<Booking> bookingList = adverts.stream()
        .flatMap(advert1 -> advert1.getBookings().stream())
        .toList();

    LocalDate startDate = entity.getDateStart();
    LocalDate finishDate = entity.getDateFinish();

    System.out.println(bookingList);

    for (Booking elem : bookingList) {
      if (!finishDate.isBefore(elem.getDateStart())
          && !startDate.isAfter(elem.getDateFinish())) {
        throw new UnavailableBookingPeriod("Бронирование помещения на данный период не доступно.");
      }
    }

    Booking booking = repository.save(entity);
    return mapper.toDtoRs(booking);
  }

  @Override
  public PaginationDto<BookingDtoRs> getBookingsForClientByEmail(String email, Integer pageNumber) {
    if (Objects.isNull(pageNumber)) {
      pageNumber = 0;
    }
    PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE);
    Page<Booking> page = repository.findAllByClientEmail(email, pageRequest);

    return mapper.toPaginationDto(page);
  }
}
