package ca.keyin.flight_api.controller;

import ca.keyin.flight_api.repository.AircraftRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    public static record AircraftDto(Long id, String type, String airlineName, int numberOfPassengers) {}

    private final AircraftRepository aircraftRepository;

    public PassengerController(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    @GetMapping("/{id}/aircraft")
    public ResponseEntity<List<AircraftDto>> getAircraftForPassenger(@PathVariable Long id) {
        List<AircraftDto> result = aircraftRepository.findByPassengersId(id)
                .stream()
                .map(a -> new AircraftDto(a.getId(), a.getType(), a.getAirlineName(), a.getNumberOfPassengers()))
                .toList();
        return ResponseEntity.ok(result);
    }
}
