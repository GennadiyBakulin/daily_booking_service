package ru.bakulin.daily_booking_service.service;

import ru.bakulin.daily_booking_service.dto.ClientDto;

public interface ClientService {

  ClientDto save(ClientDto dto);

  void delete(Integer id);
}
