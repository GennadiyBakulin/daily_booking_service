package ru.bakulin.daily_booking_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Apartment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false)
  private String street;

  @Column(nullable = false)
  private String house;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ApartmentType apartmentType;

  @ToString.Exclude
  @OneToMany(mappedBy = "apartment", fetch = FetchType.EAGER)
  private List<Advert> adverts;

  public Apartment(String city, String street, String house, ApartmentType apartmentType) {
    this.city = city;
    this.street = street;
    this.house = house;
    this.apartmentType = apartmentType;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> oEffectiveClass = o instanceof HibernateProxy
        ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
        : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    Apartment apartment = (Apartment) o;
    return getId() != null && Objects.equals(getId(), apartment.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass().hashCode() : getClass().hashCode();
  }
}
