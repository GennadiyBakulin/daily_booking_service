package ru.bakulin.daily_booking_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.bakulin.daily_booking_service.entity.ApartmentType;

@Builder
@Data
@Schema(description = "Сущность Помещение")
public class ApartmentDto {

  @Schema(description = "Уникальный идентификатор Помещения", example = "0", nullable = true)
  private Integer id;

  @Schema(description = "Наименование города", example = "Барнаул")
  private String city;

  @Schema(description = "Наименование улицы", example = "Ленина")
  private String street;

  @Schema(description = "Наименование дома (может быть корпус)", example = "46А")
  private String house;

  @JsonProperty("apartment_type")
  @Schema(description = "Тип помещения")
  private ApartmentType apartmentType;
}
