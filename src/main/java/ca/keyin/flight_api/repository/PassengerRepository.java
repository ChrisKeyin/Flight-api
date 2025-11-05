package ca.keyin.flight_api.repository;

import ca.keyin.flight_api.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
