package ca.keyin.flight_api.repository;

import ca.keyin.flight_api.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    List<Aircraft> findByPassengersId(Long passengerId);
}
