package ru.bakulin.daily_booking_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
@Schema(description = "Сущность Бронирование (ответ)")
public class BookingDtoRs {

  @Schema(description = "Уникальный идентификатор Бронирования", example = "0",
      accessMode = Schema.AccessMode.READ_ONLY)
  private Integer id;

  @Schema(description = "Сущность Клиент", contentSchema = ClientDto.class,
      accessMode = Schema.AccessMode.READ_ONLY)
  private ClientDto client;

  @Schema(description = "Сущность Объявление", contentSchema = AdvertDtoRs.class,
      accessMode = Schema.AccessMode.READ_ONLY)
  private AdvertDtoRs advert;

  @JsonProperty("date_start")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Schema(description = "Дата начала бронирования", example = "2025-02-13",
      accessMode = Schema.AccessMode.READ_ONLY)
  private LocalDate dateStart;

  @JsonProperty("date_finish")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Schema(description = "Дата окончания бронирования", example = "2025-02-13",
      accessMode = Schema.AccessMode.READ_ONLY)
  private LocalDate dateFinish;

  @JsonProperty("result_price")
  @Schema(description = "Результирующая цена бронирования", example = "100",
      accessMode = Schema.AccessMode.READ_ONLY)
  private BigDecimal resultPrice;
}
