package ru.bakulin.daily_booking_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class BookingDtoRs {

  private Integer id;
  private ClientDto client;
  private AdvertDtoRs advert;
  private LocalDate dateStart;
  private LocalDate dateFinish;
  private BigDecimal resultPrice;
}
