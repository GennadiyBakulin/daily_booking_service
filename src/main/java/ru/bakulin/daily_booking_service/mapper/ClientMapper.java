package ru.bakulin.daily_booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bakulin.daily_booking_service.dto.ClientDto;
import ru.bakulin.daily_booking_service.entity.Client;

@Mapper
public interface ClientMapper {

  @Mapping(target = "id", ignore = true)
  Client toEntity(ClientDto clientDto);

  ClientDto toDto(Client client);
}
