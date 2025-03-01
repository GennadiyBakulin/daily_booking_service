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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.transaction.annotation.Transactional;
import ru.bakulin.daily_booking_service.dto.ApartmentDto;
import ru.bakulin.daily_booking_service.entity.ApartmentType;
import ru.bakulin.daily_booking_service.service.ApartmentService;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@Transactional
@Rollback
@Sql(value = "classpath:clear-table.sql", executionPhase = ExecutionPhase.AFTER_TEST_CLASS)
class ApartmentControllerTest {

  @Autowired
  private ApartmentService service;

  private final RequestSpecification requestSpecification = new RequestSpecBuilder()
      .setBasePath("/apartment")
      .log(LogDetail.ALL)
      .build()
      .contentType("application/json");

  private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
      .log(LogDetail.ALL)
      .build();

  @Test
  @DisplayName("Успешное сохранение Помещения в БД")
  public void successSaveApartment() {

    ApartmentDto request = ApartmentDto.builder()
        .city("Barnaul")
        .street("Lenina")
        .house("46")
        .apartmentType(ApartmentType.ONLY_ROOM)
        .build();

    ApartmentDto response = RestAssured.given(requestSpecification)
        .body(request)
        .post()
        .then()
        .spec(responseSpecification)
        .statusCode(200)
        .extract()
        .body()
        .as(ApartmentDto.class);

    Assertions.assertEquals(request.getCity(), response.getCity());
    Assertions.assertEquals(request.getStreet(), response.getStreet());
    Assertions.assertEquals(request.getHouse(), response.getHouse());
    Assertions.assertEquals(request.getApartmentType(), response.getApartmentType());
  }

}