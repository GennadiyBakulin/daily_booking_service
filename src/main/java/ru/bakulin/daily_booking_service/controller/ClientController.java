package ru.bakulin.daily_booking_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bakulin.daily_booking_service.service.ClientService;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

  private final ClientService service;

  @DeleteMapping("/{id}")
  public void deleteClient(@PathVariable Integer id) {
    service.delete(id);
  }
}
