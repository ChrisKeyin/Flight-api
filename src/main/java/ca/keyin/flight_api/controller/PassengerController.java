package ca.keyin.flight_api.controller;

import ca.keyin.flight_api.repository.AircraftRepository;
import ca.keyin.flight_api.repository.AirportRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    public static record AircraftDto(Long id, String type, String airlineName, int numberOfPassengers) {}
    public static record AirportDto(Long id, String code, String name) {}

    private final AircraftRepository aircraftRepository;
    private final AirportRepository airportRepository;

    public PassengerController(AircraftRepository aircraftRepository,
                               AirportRepository airportRepository) {
        this.aircraftRepository = aircraftRepository;
        this.airportRepository = airportRepository;
    }

    @GetMapping("/{id}/aircraft")
    public ResponseEntity<List<AircraftDto>> getAircraftForPassenger(@PathVariable Long id) {
        var result = aircraftRepository.findByPassengersId(id)
                .stream()
                .map(a -> new AircraftDto(a.getId(), a.getType(), a.getAirlineName(), a.getNumberOfPassengers()))
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/airports-used")
    public ResponseEntity<List<AirportDto>> getAirportsUsedByPassenger(@PathVariable Long id) {
        var result = airportRepository.findAirportsUsedByPassenger(id)
                .stream()
                .map(a -> new AirportDto(a.getId(), a.getCode(), a.getName()))
                .toList();
        return ResponseEntity.ok(result);
    }
}
