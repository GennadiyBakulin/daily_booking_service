package ru.bakulin.daily_booking_service.service;

import ru.bakulin.daily_booking_service.dto.ClientDto;
import ru.bakulin.daily_booking_service.entity.Client;

public interface ClientService {

  ClientDto save(ClientDto dto);

  Client findById(Integer id);
}
