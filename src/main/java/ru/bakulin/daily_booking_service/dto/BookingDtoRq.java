package ru.bakulin.daily_booking_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "Сущность Бронирование (запрос)")
public class BookingDtoRq {

  @Schema(description = "Уникальный идентификатор Бронирования", nullable = true)
  private Integer id;

  @Schema(description = "Сущность Клиент")
  private ClientDto client;

  @JsonProperty("advert_id")
  @Schema(description = "Уникальный идентификатор Объявления", example = "0")
  private Integer advertId;

  @JsonProperty("date_start")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Schema(description = "Дата начала бронирования", example = "2025-02-13")
  private LocalDate dateStart;

  @JsonProperty("date_finish")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Schema(description = "Дата окончания бронирования", example = "2025-02-13")
  private LocalDate dateFinish;
}
