package ru.bakulin.daily_booking_service.controller;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import ru.bakulin.daily_booking_service.repository.BookingRepository;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@Sql(value = "classpath:clear-table.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
class ClientControllerTest {

  @Autowired
  private BookingRepository bookingRepository;

  private final RequestSpecification requestSpecification = new RequestSpecBuilder()
      .setBasePath("/client")
      .log(LogDetail.ALL)
      .build()
      .contentType("application/json");

  private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
      .log(LogDetail.ALL)
      .build();

  @Test
  @DisplayName("Успешное удалении клиента и всех его бронирований")
  @Sql(value = "classpath:test-client-controller.sql")
  public void successDeleteClientWithHisBookings() {

    int startNumberBooking = bookingRepository.findAllByClientId(1).size();
    Assertions.assertEquals(5, startNumberBooking);

    RestAssured.given(requestSpecification)
        .pathParam("id", 1)
        .delete("/{id}")
        .then()
        .spec(responseSpecification)
        .statusCode(200);

    int endNumberBooking = bookingRepository.findAllByClientId(1).size();
    Assertions.assertEquals(0, endNumberBooking);
  }
}