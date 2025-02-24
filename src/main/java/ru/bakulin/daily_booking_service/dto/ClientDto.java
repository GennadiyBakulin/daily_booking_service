package ru.bakulin.daily_booking_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClientDto {

  @JsonIgnoreProperties(ignoreUnknown = true)
  private Integer id;
  private String name;
  private String email;
}
