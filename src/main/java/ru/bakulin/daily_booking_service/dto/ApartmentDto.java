package ru.bakulin.daily_booking_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ru.bakulin.daily_booking_service.entity.ApartmentType;

@Builder
@Data
public class ApartmentDto {

  @JsonIgnoreProperties(ignoreUnknown = true)
  private Integer id;
  private String city;
  private String street;
  private String house;
  private ApartmentType apartmentType;
}
