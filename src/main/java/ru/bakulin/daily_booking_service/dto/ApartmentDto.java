package ru.bakulin.daily_booking_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.bakulin.daily_booking_service.entity.ApartmentType;

@Data
public class ApartmentDto {

  private Integer id;
  private String city;
  private String street;
  private String house;
  @JsonProperty("apartment_type")
  private ApartmentType apartmentType;
}
