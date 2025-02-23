package ru.bakulin.daily_booking_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class BookingDtoRq {

  private Integer id;
  private Integer clientId;
  private Integer advertId;
  private LocalDate dateStart;
  private LocalDate dateFinish;
  private BigDecimal resultPrice;
}
