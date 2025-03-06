package ru.bakulin.daily_booking_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRq;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRs;
import ru.bakulin.daily_booking_service.dto.PageDto;
import ru.bakulin.daily_booking_service.service.AdvertService;

@Tag(name = "Advert Controller",
    description = "API для работы с объявлениями")
@RestController
@RequestMapping("/advert")
@RequiredArgsConstructor
public class AdvertController {

  private final AdvertService service;

  @Operation(
      summary = "Создание нового Объявления",
      description = "Сохраняет новое объявление в БД и возвращает его")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "201",
              description = "Успешное сохранение объявления в БД",
              content = {
                  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                      schema = @Schema(implementation = AdvertDtoRs.class))
              }
          ),
          @ApiResponse(
              responseCode = "404",
              description = "Отмена сохранения объявления в случае если не найдено переданное помещение с указанным Id",
              content = {
                  @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                      schema = @Schema(implementation = String.class))
              }
          )
      }
  )
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AdvertDtoRs create(@RequestBody AdvertDtoRq dto) {
    return service.save(dto);
  }

  @Operation(
      summary = "Постраничное получение списка объявлений",
      description = "Получает список объявлений по указанному городу отсортированных по цене бронирования по 10 штук")
  @ApiResponse(
      responseCode = "200",
      description = "Успешное получение списка",
      content = {
          @Content(mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = PageDto.class))
          )
      }
  )
  @GetMapping
  public PageDto<AdvertDtoRs> findAllByCity(
      @RequestParam @Parameter(description = "Наименование города", required = true) String city,
      @RequestParam(required = false) @Parameter(description = "Номер страницы", example = "1") Integer page
  ) {

    return service.findAllByCity(city, page);
  }
}
