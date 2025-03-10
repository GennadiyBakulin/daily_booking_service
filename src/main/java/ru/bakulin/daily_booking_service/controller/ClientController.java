package ru.bakulin.daily_booking_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bakulin.daily_booking_service.service.ClientService;

@Tag(name = "Client Controller",
    description = "API для работы с клиентами")
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

  private final ClientService service;

  @Operation(
      summary = "Удаление клиента из БД",
      description = "Удаляет клиента и его брони из БД по переданному Id клиента")
  @ApiResponse(
      responseCode = "200",
      description = "Успешное удаление клиента из БД"
  )
  @DeleteMapping("/{id}")
  public void deleteClient(
      @PathVariable @Parameter(description = "Идентификатор клиента", example = "1", required = true) Integer id
  ) {
    service.delete(id);
  }
}
