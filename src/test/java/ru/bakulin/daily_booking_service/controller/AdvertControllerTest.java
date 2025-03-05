package ru.bakulin.daily_booking_service.controller;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.math.BigDecimal;
import java.util.List;
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
import ru.bakulin.daily_booking_service.dto.PageDto;
import ru.bakulin.daily_booking_service.entity.ApartmentType;
import ru.bakulin.daily_booking_service.service.ApartmentService;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@Sql(value = "classpath:sql/clear-table.sql", executionPhase = ExecutionPhase.AFTER_TEST_CLASS)
class AdvertControllerTest {

  @Autowired
  private ApartmentService apartmentService;

  private final RequestSpecification requestSpecification = new RequestSpecBuilder()
      .setBasePath("/advert")
      .log(LogDetail.ALL)
      .build()
      .contentType("application/json");

  private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
      .log(LogDetail.ALL)
      .build();

  @Test
  @DisplayName("Успешное сохранение Объявления в БД")
  @Sql(value = "classpath:sql/clear-table.sql")
  public void successSaveAdvert() {
    ApartmentDto apartmentDto = ApartmentDto.builder()
        .city("Barnaul")
        .street("Lenina")
        .house("46")
        .apartmentType(ApartmentType.ONLY_ROOM)
        .build();

    ApartmentDto apartment = apartmentService.save(apartmentDto);

    AdvertDtoRq request = AdvertDtoRq.builder()
        .price(BigDecimal.valueOf(1000))
        .isActive(true)
        .apartmentId(apartment.getId())
        .description("Описание объявления")
        .build();

    AdvertDtoRs response = RestAssured.given(requestSpecification)
        .body(request)
        .post()
        .then()
        .spec(responseSpecification)
        .statusCode(200)
        .extract()
        .body()
        .as(AdvertDtoRs.class);
    ApartmentDto responseApartment = response.getApartment();

    Assertions.assertEquals(request.getPrice(), response.getPrice());
    Assertions.assertEquals(request.getIsActive(), response.getIsActive());
    Assertions.assertEquals(request.getDescription(), response.getDescription());
    Assertions.assertEquals(responseApartment.getId(), apartment.getId());
  }

  @Test
  @DisplayName("Успешное получение списка Объявлений по 10 штук и сортировка списка Объявлений по убывающей цене")
  @Sql(value = {"classpath:sql/clear-table.sql", "classpath:sql/test-advert-controller.sql"})
  public void successGetPaginationAdverts() {
    PageDto<AdvertDtoRs> response = RestAssured.given(requestSpecification)
        .queryParam("city", "Barnaul")
        .queryParam("page", 0)
        .get()
        .then()
        .spec(responseSpecification)
        .statusCode(200)
        .extract()
        .body()
        .as(new TypeRef<>() {
        });

    Assertions.assertEquals(10, response.getContent().size());
    Assertions.assertEquals(15, response.getTotalElements());
    Assertions.assertEquals(2, response.getTotalPages());
    Assertions.assertEquals(0, response.getNumber());

    List<AdvertDtoRs> adverts = response.getContent();

    Assertions.assertEquals(0, BigDecimal.valueOf(1050).compareTo(adverts.get(0).getPrice()));
    Assertions.assertEquals(0, BigDecimal.valueOf(600).compareTo(adverts.get(9).getPrice()));
  }
}
