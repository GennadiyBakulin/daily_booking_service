package ru.bakulin.daily_booking_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Schema(description = "Сущность Объявление (ответ)")
public class AdvertDtoRs {

  @Schema(description = "Уникальный идентификатор Объявления", example = "0")
  private Integer id;

  @Schema(description = "Цена бронирования за ночь", example = "100")
  private BigDecimal price;

  @JsonProperty("is_active")
  @Schema(description = "статус (активно или архивное)", example = "true",
      allowableValues = {"true", "false"})
  private Boolean isActive;

  @Schema(description = "Сущность Помещения")
  private ApartmentDto apartment;

  @Schema(description = "Описание объявления")
  private String description;
}
