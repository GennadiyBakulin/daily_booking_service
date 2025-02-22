package ru.bakulin.daily_booking_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;
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
public class Advert {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private BigDecimal price;

  @Column(nullable = false)
  private Boolean isActive;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "apartment_id", nullable = false)
  private Apartment apartment;

  @Column(nullable = false)
  private String description;

  @ToString.Exclude
  @OneToMany(mappedBy = "advert", fetch = FetchType.EAGER)
  private List<Booking> bookings;

  public Advert(BigDecimal price, Boolean isActive, Apartment apartment, String description) {
    this.price = price;
    this.isActive = isActive;
    this.apartment = apartment;
    this.description = description;
  }
}
