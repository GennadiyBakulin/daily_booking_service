package ru.bakulin.daily_booking_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "Сущность Объявление (запрос)")
public class AdvertDtoRq {

  @Schema(description = "Уникальный идентификатор Объявления", nullable = true)
  private Integer id;

  @Schema(description = "Цена бронирования за ночь", example = "100",
      minimum = "0", exclusiveMinimum = true)
  private BigDecimal price;

  @JsonProperty("is_active")
  @Schema(description = "статус (активно или архивное)", example = "true",
      allowableValues = {"true", "false"})
  private Boolean isActive;

  @JsonProperty("apartment_id")
  @Schema(description = "Уникальный идентификатор Помещения", example = "0")
  private Integer apartmentId;

  @Schema(description = "Описание объявления")
  private String description;
}
