package ru.bakulin.daily_booking_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class AdvertDtoRs {

  private Integer id;
  private BigDecimal price;
  @JsonProperty("is_active")
  private Boolean isActive;
  private ApartmentDto apartment;
  private String description;
}
