package ru.bakulin.daily_booking_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bakulin.daily_booking_service.dto.ClientDto;
import ru.bakulin.daily_booking_service.entity.Client;
import ru.bakulin.daily_booking_service.mapper.ClientMapper;
import ru.bakulin.daily_booking_service.repository.ClientRepository;
import ru.bakulin.daily_booking_service.service.ClientService;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

  private final ClientRepository repository;
  private final ClientMapper mapper;

  public ClientDto save(ClientDto dto) {
    Client entity = mapper.toEntity(dto);
    Client client = repository.save(entity);
    return mapper.toDto(client);
  }
}
