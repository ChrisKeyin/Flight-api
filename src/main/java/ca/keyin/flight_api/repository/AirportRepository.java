package ca.keyin.flight_api.repository;

import ca.keyin.flight_api.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
}
