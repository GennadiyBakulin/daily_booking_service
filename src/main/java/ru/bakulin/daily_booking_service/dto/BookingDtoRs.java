package ru.bakulin.daily_booking_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class BookingDtoRs {

  private Integer id;
  private ClientDto client;
  private AdvertDtoRs advert;
  @JsonProperty("date_start")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateStart;
  @JsonProperty("date_finish")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateFinish;
  @JsonProperty("result_price")
  private BigDecimal resultPrice;
}
