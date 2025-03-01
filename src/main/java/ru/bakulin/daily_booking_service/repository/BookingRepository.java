package ru.bakulin.daily_booking_service.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.bakulin.daily_booking_service.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

  Page<Booking> findAllByClientEmail(String email, Pageable pageable);

  List<Booking> findAllByAdvertApartmentAdvertsBookings(Booking bookings);
}
