package ru.bakulin.daily_booking_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookingDtoRq {

  private Integer id;
  private Integer clientId;
  private Integer advertId;
  private String dateStart;
  private String dateFinish;
}
