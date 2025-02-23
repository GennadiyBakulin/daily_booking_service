package ru.bakulin.daily_booking_service.service.impl;

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

    Booking entity = mapper.toEntity(dtoRq);
    entity.setClient(client);
    entity.setAdvert(advert);

    Booking booking = repository.save(entity);

    return mapper.toDtoRs(booking);
  }
}
