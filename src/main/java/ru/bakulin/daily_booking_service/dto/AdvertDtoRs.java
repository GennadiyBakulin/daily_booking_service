package ru.bakulin.daily_booking_service.dto;

import java.math.BigDecimal;
import lombok.Data;
import ru.bakulin.daily_booking_service.entity.Apartment;

@Data
public class AdvertDtoRs {

  private Integer id;
  private BigDecimal price;
  private Boolean isActive;
  private Apartment apartment;
  private String description;
}
