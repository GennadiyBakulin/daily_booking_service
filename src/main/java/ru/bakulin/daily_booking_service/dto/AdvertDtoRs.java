package ru.bakulin.daily_booking_service.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AdvertDtoRs {

  private Integer id;
  private BigDecimal price;
  private Boolean isActive;
  private ApartmentDto apartment;
  private String description;
}
