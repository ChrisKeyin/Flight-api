package ca.keyin.flight_api.repository;

import ca.keyin.flight_api.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> { }
