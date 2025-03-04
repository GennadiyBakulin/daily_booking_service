package ru.bakulin.daily_booking_service.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.bakulin.daily_booking_service.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

  Page<Booking> findAllByClientEmail(String email, Pageable pageable);

  List<Booking> findAllByClientId(Integer id);

  @Query(value = """
      with booked_dates as (
            select start_date, end_date
            from booking join  advert on booking.advert_id = advert.id
            where advert.apartment_id = (select apartment_id from advert where id = ?))
      select count(*) > 0
      from booked_dates
      where ? <= booked_dates.end_date and ? >= booked_dates.start_date
      """, nativeQuery = true)
  boolean existsIntersectionWithBookings(Integer advertId, LocalDate start, LocalDate finish);
}
