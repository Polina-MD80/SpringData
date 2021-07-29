package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import softuni.exam.models.entity.Passenger;

import java.util.List;


public interface PassengerRepository  extends JpaRepository<Passenger,Long> {
    boolean existsByEmail(String email);
    Passenger findByEmail(String email);
    @Query("select p from Passenger p " +
            "order by size(p.ticketSet)desc, p.email")
    List<Passenger> findOrderedPassengers();
}
