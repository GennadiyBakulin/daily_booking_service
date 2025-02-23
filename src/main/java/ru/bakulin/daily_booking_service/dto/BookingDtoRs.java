package ru.bakulin.daily_booking_service.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BookingDtoRs {

  private Integer id;
  private ClientDto client;
  private AdvertDtoRs advert;
  private String dateStart;
  private String dateFinish;
  private BigDecimal resultPrice;
}
