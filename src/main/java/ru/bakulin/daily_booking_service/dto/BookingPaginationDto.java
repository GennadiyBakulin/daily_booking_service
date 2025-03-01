package ru.bakulin.daily_booking_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class BookingPaginationDto {

  private List<BookingDtoRs> bookings;
  @JsonProperty("total_elements")
  private long totalElements;
  @JsonProperty("total_pages")
  private int totalPages;
  @JsonProperty("number_page")
  private int numberPage;
}
