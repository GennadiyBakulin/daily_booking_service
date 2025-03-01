package ru.bakulin.daily_booking_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AdvertDtoRq {

  private Integer id;
  private BigDecimal price;
  @JsonProperty("is_active")
  private Boolean isActive;
  @JsonProperty("apartment_id")
  private Integer apartmentId;
  private String description;
}
