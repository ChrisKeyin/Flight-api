package ca.keyin.flight_api.controller;

import ca.keyin.flight_api.entity.Aircraft;
import ca.keyin.flight_api.repository.AircraftRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aircraft")
public class AircraftController {

    public static record AirportDto(Long id, String name, String code) {}

    private final AircraftRepository aircraftRepository;

    public AircraftController(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    @GetMapping("/{id}/airports")
    public ResponseEntity<List<AirportDto>> getAirportsForAircraft(@PathVariable Long id) {
        Aircraft ac = aircraftRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aircraft not found: " + id));

        var result = ac.getAirports().stream()
                .map(a -> new AirportDto(a.getId(), a.getName(), a.getCode()))
                .toList();

        return ResponseEntity.ok(result);
    }
}
