package ru.bakulin.daily_booking_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AdvertDtoRq {

  @JsonIgnoreProperties(ignoreUnknown = true)
  private Integer id;
  private BigDecimal price;
  private Boolean isActive;
  private Integer apartmentId;
  private String description;
}
