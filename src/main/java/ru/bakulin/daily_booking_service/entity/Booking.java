package ru.bakulin.daily_booking_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(nullable = false)
  private LocalDate startDate;
  @Column(nullable = false)
  private LocalDate endDate;
  @Column(nullable = false)
  private Integer clientId;
  @Column(nullable = false)
  private Integer advertId;
  @Column(nullable = false)
  private BigDecimal amount;
}
