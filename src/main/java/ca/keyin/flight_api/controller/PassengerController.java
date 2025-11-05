package ca.keyin.flight_api.controller;

import ca.keyin.flight_api.entity.Aircraft;
import ca.keyin.flight_api.entity.Airport;
import ca.keyin.flight_api.entity.Passenger;
import ca.keyin.flight_api.repository.PassengerRepository;
import ca.keyin.flight_api.repository.AircraftRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping
public class PassengerController {

    private final PassengerRepository passengerRepository;
    private final AircraftRepository aircraftRepository;

    public PassengerController(PassengerRepository passengerRepository,
                               AircraftRepository aircraftRepository) {
        this.passengerRepository = passengerRepository;
        this.aircraftRepository = aircraftRepository;
    }

    public static record PassengerDto(Long id, String firstName, String lastName, String phoneNumber) {}
    public static record AircraftDto(Long id, String type, String airlineName, int numberOfPassengers) {}
    public static record AirportDto(Long id, String name, String code) {}

    @GetMapping("/passengers")
    @Transactional(readOnly = true)
    public ResponseEntity<List<PassengerDto>> listPassengers() {
        List<PassengerDto> out = new ArrayList<>();
        for (Passenger p : passengerRepository.findAll()) {
            out.add(new PassengerDto(p.getId(), p.getFirstName(), p.getLastName(), p.getPhoneNumber()));
        }
        return ResponseEntity.ok(out);
    }

    @GetMapping("/passengers/{id}/aircraft")
    @Transactional(readOnly = true)
    public ResponseEntity<List<AircraftDto>> getAircraftForPassenger(@PathVariable Long id) {
        Optional<Passenger> opt = passengerRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Passenger p = opt.get();

        List<AircraftDto> out = new ArrayList<>();
        for (Aircraft a : p.getAircraftList()) {
            out.add(new AircraftDto(a.getId(), a.getType(), a.getAirlineName(), a.getNumberOfPassengers()));
        }
        return ResponseEntity.ok(out);
    }

    @GetMapping("/passengers/{id}/airports")
    @Transactional(readOnly = true)
    public ResponseEntity<List<AirportDto>> getAirportsForPassenger(@PathVariable Long id) {
        Optional<Passenger> opt = passengerRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Passenger p = opt.get();

        Map<Long, AirportDto> unique = new LinkedHashMap<>();
        for (Aircraft a : p.getAircraftList()) {
            for (Airport ap : a.getAirports()) {
                unique.putIfAbsent(ap.getId(), new AirportDto(ap.getId(), ap.getName(), ap.getCode()));
            }
        }

        return ResponseEntity.ok(new ArrayList<>(unique.values()));
    }
}
