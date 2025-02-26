package ru.bakulin.daily_booking_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.bakulin.daily_booking_service.entity.Advert;

public interface AdvertRepository extends JpaRepository<Advert, Integer> {

  Page<Advert> findAllByApartmentCityOrderByPriceDesc(String city, Pageable pageable);

}
