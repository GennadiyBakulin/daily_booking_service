package ru.bakulin.daily_booking_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "Сущность Клиент")
public class ClientDto {

  @Schema(description = "Уникальный идентификатор Клиента", example = "0", nullable = true)
  private Integer id;

  @Schema(description = "Имя клиента", example = "Петр")
  private String name;

  @Schema(description = "Email клиента", example = "my-email@mail.ru")
  private String email;
}
