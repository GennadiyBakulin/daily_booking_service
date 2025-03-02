package ru.bakulin.daily_booking_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bakulin.daily_booking_service.dto.ApartmentDto;
import ru.bakulin.daily_booking_service.service.ApartmentService;

@Tag(name = "Apartment Controller",
    description = "API для работы с помещениями")
@RestController
@RequestMapping("/apartment")
@RequiredArgsConstructor
public class ApartmentController {

  private final ApartmentService service;

  @Operation(
      summary = "Создание нового помещения в БД",
      description = "Сохраняет и возвращает вновь созданное помещение в БД")
  @ApiResponse(
      responseCode = "200",
      description = "Успешное сохранение помещения в БД",
      content = {
          @Content(mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApartmentDto.class))
          )
      }
  )
  @PostMapping
  public ApartmentDto create(@RequestBody ApartmentDto dto) {
    return service.save(dto);
  }
}
