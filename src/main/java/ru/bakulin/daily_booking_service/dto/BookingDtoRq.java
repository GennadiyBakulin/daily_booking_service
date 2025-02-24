package ru.bakulin.daily_booking_service.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BookingDtoRq {

  private Integer id;
  private Integer clientId;
  private Integer advertId;
  private String dateStart;
  private String dateFinish;
}
