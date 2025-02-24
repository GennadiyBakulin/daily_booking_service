package ru.bakulin.daily_booking_service.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AdvertDtoRq {

  private Integer id;
  private BigDecimal price;
  private Boolean isActive;
  private Integer apartmentId;
  private String description;
}
