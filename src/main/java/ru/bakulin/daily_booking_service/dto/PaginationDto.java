package ru.bakulin.daily_booking_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
@Schema(description = "Сущность Постраничный вывод")
public class PaginationDto<T> {

  @Schema(description = "Список содержимого страницы", contentSchema = List.class,
      accessMode = Schema.AccessMode.READ_ONLY)
  private List<T> content;

  @JsonProperty("total_elements")
  @Schema(description = "Общее количество элементов на всех страницах", example = "100",
      accessMode = Schema.AccessMode.READ_ONLY)
  private long totalElements;

  @JsonProperty("total_pages")
  @Schema(description = "Общее количество страниц", example = "10",
      accessMode = Schema.AccessMode.READ_ONLY)
  private int totalPages;

  @JsonProperty("number_page")
  @Schema(description = "Номер текущей страницы", example = "1",
      accessMode = Schema.AccessMode.READ_ONLY)
  private int numberPage;
}
