package ru.bakulin.daily_booking_service.repository;

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
      select *
      from booking
      where advert_id in
            (select id
             from advert
             where apartment_id =
                   (select apartment_id
                    from advert
                    where id = ?))
      """, nativeQuery = true)
  List<Booking> findAllForCurrentApartment(Integer advertId);
}
