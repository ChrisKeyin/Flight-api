package ca.keyin.flight_api.controller;

import ca.keyin.flight_api.entity.City;
import ca.keyin.flight_api.entity.Passenger;
import ca.keyin.flight_api.repository.CityRepository;
import ca.keyin.flight_api.repository.PassengerRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/admin/passengers")
@Transactional
public class PassengerCrudController {

    private final PassengerRepository passengerRepository;
    private final CityRepository cityRepository;

    public PassengerCrudController(PassengerRepository passengerRepository,
                                   CityRepository cityRepository) {
        this.passengerRepository = passengerRepository;
        this.cityRepository = cityRepository;
    }

    public record CreateOrUpdate(String firstName, String lastName, String phoneNumber, Long cityId) {}
    public record PassengerResp(Long id, String firstName, String lastName, String phoneNumber, Long cityId) {}
    @GetMapping
    @Transactional(readOnly = true)
    public List<PassengerResp> list() {
        return passengerRepository.findAll().stream()
                .map(p -> new PassengerResp(
                        p.getId(), p.getFirstName(), p.getLastName(),
                        p.getPhoneNumber(),
                        p.getCity() != null ? p.getCity().getId() : null))
                .toList();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public PassengerResp get(@PathVariable Long id) {
        Passenger p = passengerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Passenger not found"));
        return new PassengerResp(
                p.getId(), p.getFirstName(), p.getLastName(), p.getPhoneNumber(),
                p.getCity() != null ? p.getCity().getId() : null
        );
    }

    @PostMapping
    public PassengerResp create(@RequestBody @Valid CreateOrUpdate in) {
        Passenger p = new Passenger();
        p.setFirstName(in.firstName());
        p.setLastName(in.lastName());
        p.setPhoneNumber(in.phoneNumber());

        if (in.cityId() != null) {
            City c = cityRepository.findById(in.cityId())
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "City not found"));
            p.setCity(c);
        }

        p = passengerRepository.save(p);
        return new PassengerResp(
                p.getId(), p.getFirstName(), p.getLastName(), p.getPhoneNumber(),
                p.getCity() != null ? p.getCity().getId() : null
        );
    }

    @PutMapping("/{id}")
    public PassengerResp update(@PathVariable Long id, @RequestBody @Valid CreateOrUpdate in) {
        Passenger p = passengerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Passenger not found"));

        p.setFirstName(in.firstName());
        p.setLastName(in.lastName());
        p.setPhoneNumber(in.phoneNumber());

        if (in.cityId() != null) {
            City c = cityRepository.findById(in.cityId())
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "City not found"));
            p.setCity(c);
        } else {
            p.setCity(null);
        }

        p = passengerRepository.save(p);
        return new PassengerResp(
                p.getId(), p.getFirstName(), p.getLastName(), p.getPhoneNumber(),
                p.getCity() != null ? p.getCity().getId() : null
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Passenger not found");
        }
        passengerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
