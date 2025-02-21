package ru.bakulin.daily_booking_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private LocalDate startDate;

  @Column(nullable = false)
  private LocalDate endDate;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "advert_id", nullable = false)
  private Advert advert;

  @Column(nullable = false)
  private BigDecimal amount;

  public Booking(LocalDate startDate, LocalDate endDate, Client client, Advert advert,
      BigDecimal amount) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.client = client;
    this.advert = advert;
    this.amount = amount;
  }
}
