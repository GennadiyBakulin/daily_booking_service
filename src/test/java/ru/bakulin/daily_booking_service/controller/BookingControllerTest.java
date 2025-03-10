package ru.bakulin.daily_booking_service.controller;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRq;
import ru.bakulin.daily_booking_service.dto.AdvertDtoRs;
import ru.bakulin.daily_booking_service.dto.ApartmentDto;
import ru.bakulin.daily_booking_service.dto.BookingDtoRq;
import ru.bakulin.daily_booking_service.dto.BookingDtoRs;
import ru.bakulin.daily_booking_service.dto.ClientDto;
import ru.bakulin.daily_booking_service.entity.ApartmentType;
import ru.bakulin.daily_booking_service.service.AdvertService;
import ru.bakulin.daily_booking_service.service.ApartmentService;
import ru.bakulin.daily_booking_service.service.ClientService;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@Sql(value = "classpath:sql/clear-table.sql", executionPhase = ExecutionPhase.AFTER_TEST_CLASS)
class BookingControllerTest {

  @Autowired
  private ApartmentService apartmentService;

  @Autowired
  private AdvertService advertService;

  @Autowired
  private ClientService clientService;

  private final RequestSpecification requestSpecification = new RequestSpecBuilder()
      .setBasePath("/booking")
      .log(LogDetail.ALL)
      .build()
      .contentType("application/json");

  private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
      .log(LogDetail.ALL)
      .build();

  private final ClientDto clientDto = ClientDto.builder()
      .name("Gennadiy")
      .email("my_email@yandex.ru")
      .build();

  private final ApartmentDto apartmentDto = ApartmentDto.builder()
      .city("Barnaul")
      .street("Lenina")
      .house("46")
      .apartmentType(ApartmentType.ONLY_ROOM)
      .build();

  @Test
  @DisplayName("Успешное бронирование, при незаполненности id у клиента")
  @Sql(value = "classpath:sql/clear-table.sql")
  public void successBookingIfClientIdIsNull() {
    ApartmentDto apartment = apartmentService.save(apartmentDto);

    AdvertDtoRq advertDto = AdvertDtoRq.builder()
        .price(BigDecimal.valueOf(100))
        .isActive(true)
        .apartmentId(apartment.getId())
        .description("Описание объявления")
        .build();
    AdvertDtoRs advert = advertService.save(advertDto);

    BookingDtoRq request = BookingDtoRq.builder()
        .client(clientDto)
        .advertId(advert.getId())
        .dateStart(LocalDate.of(2025, 11, 1))
        .dateFinish(LocalDate.of(2025, 11, 10))
        .build();

    BookingDtoRs response = RestAssured.given(requestSpecification)
        .body(request)
        .post()
        .then()
        .spec(responseSpecification)
        .statusCode(201)
        .extract()
        .body()
        .as(BookingDtoRs.class);

    Assertions.assertNotNull(response.getClient().getId());
    Assertions.assertEquals(request.getClient().getName(), response.getClient().getName());
    Assertions.assertEquals(request.getClient().getEmail(), response.getClient().getEmail());

    BigDecimal price = response.getAdvert().getPrice();
    long periodBooking = ChronoUnit.DAYS.between(response.getDateStart(), response.getDateFinish());
    BigDecimal expectedResultPrice = price.multiply(BigDecimal.valueOf(periodBooking));

    Assertions.assertEquals(expectedResultPrice, response.getResultPrice());
  }

  @Test
  @DisplayName("Успешное бронирование, при указанном id у клиента")
  @Sql(value = "classpath:sql/clear-table.sql")
  public void successBookingIfClientIdIsNotNull() {
    ClientDto client = clientService.save(clientDto);

    ApartmentDto apartment = apartmentService.save(apartmentDto);

    AdvertDtoRq advertDto = AdvertDtoRq.builder()
        .price(BigDecimal.valueOf(100))
        .isActive(true)
        .apartmentId(apartment.getId())
        .description("Описание объявления")
        .build();
    AdvertDtoRs advert = advertService.save(advertDto);

    BookingDtoRq request = BookingDtoRq.builder()
        .client(client)
        .advertId(advert.getId())
        .dateStart(LocalDate.of(2025, 11, 1))
        .dateFinish(LocalDate.of(2025, 11, 10))
        .build();

    BookingDtoRs response = RestAssured.given(requestSpecification)
        .body(request)
        .post()
        .then()
        .spec(responseSpecification)
        .statusCode(201)
        .extract()
        .body()
        .as(BookingDtoRs.class);

    Assertions.assertEquals(request.getClient(), response.getClient());
    Assertions.assertNotNull(response.getId());

    BigDecimal price = response.getAdvert().getPrice();
    long periodBooking = ChronoUnit.DAYS.between(response.getDateStart(), response.getDateFinish());
    BigDecimal expectedResultPrice = price.multiply(BigDecimal.valueOf(periodBooking));

    Assertions.assertEquals(expectedResultPrice, response.getResultPrice());
  }

  @Test
  @DisplayName("Неуспешное бронирование при существующем бронировании\n"
      + "  на эти даты: с 05.10 по 06.10")
  @Sql(value = {"classpath:sql/clear-table.sql", "classpath:sql/test-booking-controller.sql"})
  public void notSuccessBookingOnDataBetween0510To0610() {
    ClientDto clientDto = ClientDto.builder()
        .id(1)
        .name("Petr")
        .email("mail@mail.ru")
        .build();

    BookingDtoRq request = BookingDtoRq.builder()
        .client(clientDto)
        .advertId(1)
        .dateStart(LocalDate.of(2025, 10, 5))
        .dateFinish(LocalDate.of(2025, 10, 6))
        .build();

    RestAssured.given(requestSpecification)
        .body(request)
        .post()
        .then()
        .spec(responseSpecification)
        .statusCode(400);
  }

  @Test
  @DisplayName("Неуспешное бронирование при существующем бронировании\n"
      + "  на эти даты: с 29.09 по 02.10")
  @Sql(value = {"classpath:sql/clear-table.sql", "classpath:sql/test-booking-controller.sql"})
  public void notSuccessBookingOnDataBetween2909To0210() {
    ClientDto clientDto = ClientDto.builder()
        .id(1)
        .name("Petr")
        .email("mail@mail.ru")
        .build();

    BookingDtoRq request = BookingDtoRq.builder()
        .client(clientDto)
        .advertId(1)
        .dateStart(LocalDate.of(2025, 9, 29))
        .dateFinish(LocalDate.of(2025, 10, 2))
        .build();

    RestAssured.given(requestSpecification)
        .body(request)
        .post()
        .then()
        .spec(responseSpecification)
        .statusCode(400);
  }

  @Test
  @DisplayName("Неуспешное бронирование при существующем бронировании\n"
      + "  на эти даты: с с 09.10 по 11.10")
  @Sql(value = {"classpath:sql/clear-table.sql", "classpath:sql/test-booking-controller.sql"})
  public void notSuccessBookingOnDataBetween0910To1110() {
    ClientDto clientDto = ClientDto.builder()
        .id(1)
        .name("Petr")
        .email("mail@mail.ru")
        .build();

    BookingDtoRq request = BookingDtoRq.builder()
        .client(clientDto)
        .advertId(1)
        .dateStart(LocalDate.of(2025, 10, 9))
        .dateFinish(LocalDate.of(2025, 10, 11))
        .build();

    RestAssured.given(requestSpecification)
        .body(request)
        .post()
        .then()
        .spec(responseSpecification)
        .statusCode(400);
  }
}
