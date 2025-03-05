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
import ru.bakulin.daily_booking_service.dto.BookingDtoRq;
import ru.bakulin.daily_booking_service.dto.BookingDtoRs;
import ru.bakulin.daily_booking_service.dto.PageDto;
import ru.bakulin.daily_booking_service.service.BookingService;

@Tag(name = "Booking Controller",
    description = "API для работы с бронированиями")
@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

  private final BookingService service;

  @Operation(
      summary = "Создает новую бронь",
      description = "Создает и возвращает новое бронирование в БД")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "201",
              description = "Успешное создание и сохранение брони",
              content = {
                  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                      schema = @Schema(implementation = BookingDtoRs.class))
              }
          ),
          @ApiResponse(
              responseCode = "400",
              description = "Отмена создания брони в случае если по переданному помещению на указанные даты все занято",
              content = {
                  @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                      schema = @Schema(implementation = String.class))
              }
          ),
          @ApiResponse(
              responseCode = "404",
              description = "Отмена создания брони в случае если не найден переданные клиент или объявление по Id",
              content = {
                  @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                      schema = @Schema(implementation = String.class))
              }
          )
      }
  )
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookingDtoRs create(@RequestBody BookingDtoRq dto) {
    return service.save(dto);
  }

  @Operation(
      summary = "Постраничное получение списка бронирований",
      description = "Получает список бронирований по переданному email клиента по 20 штук")
  @ApiResponse(
      responseCode = "200",
      description = "Успешное получение списка бронирований",
      content = {
          @Content(mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = PageDto.class))
          )
      }
  )
  @GetMapping
  public PageDto<BookingDtoRs> findAllByClientEmail(
      @RequestParam @Parameter(description = "email клиента", required = true) String email,
      @RequestParam(required = false) @Parameter(description = "Номер страницы", example = "1") Integer page
  ) {

    return service.findAllByClientEmail(email, page);
  }
}
