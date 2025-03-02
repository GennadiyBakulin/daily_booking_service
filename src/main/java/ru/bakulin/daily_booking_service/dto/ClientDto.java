package ru.bakulin.daily_booking_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClientDto {

  private Integer id;
  private String name;
  private String email;
}
