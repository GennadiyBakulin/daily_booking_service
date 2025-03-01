package ru.bakulin.daily_booking_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Data;

@Data
public class BookingDtoRq {

  private Integer id;
  private ClientDto client;
  @JsonProperty("advert_id")
  private Integer advertId;
  @JsonProperty("date_start")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateStart;
  @JsonProperty("date_finish")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateFinish;
}
