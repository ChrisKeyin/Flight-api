package ca.keyin.flight_api.repository;

import ca.keyin.flight_api.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    List<Airport> findByCityId(Long cityId);

    @Query("""
           select distinct ap
           from Passenger p
             join p.aircraftList ac
             join ac.airports ap
           where p.id = :passengerId
           """)
    List<Airport> findAirportsUsedByPassenger(@Param("passengerId") Long passengerId);
}
