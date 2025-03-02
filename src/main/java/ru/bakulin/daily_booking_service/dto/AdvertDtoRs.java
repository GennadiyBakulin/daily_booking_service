package ru.bakulin.daily_booking_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Schema(description = "Сущность Объявление (ответ)")
public class AdvertDtoRs {

  @Schema(description = "Уникальный идентификатор Объявления",
      example = "0", accessMode = Schema.AccessMode.READ_ONLY)
  private Integer id;

  @Schema(description = "Цена бронирования за ночь", example = "100",
      accessMode = Schema.AccessMode.READ_ONLY)
  private BigDecimal price;

  @JsonProperty("is_active")
  @Schema(description = "статус (активно или архивное)", example = "true",
      allowableValues = {"true", "false"}, accessMode = Schema.AccessMode.READ_ONLY)
  private Boolean isActive;

  @Schema(description = "Сущность Помещения", accessMode = Schema.AccessMode.READ_ONLY,
      contentSchema = ApartmentDto.class)
  private ApartmentDto apartment;

  @Schema(description = "Описание объявления", accessMode = Schema.AccessMode.READ_ONLY)
  private String description;
}
