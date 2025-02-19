package ru.bakulin.daily_booking_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bakulin.daily_booking_service.entity.Apartment;

public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {

}
