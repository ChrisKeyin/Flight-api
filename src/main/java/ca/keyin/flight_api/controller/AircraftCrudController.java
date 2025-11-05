package ca.keyin.flight_api.controller;

import ca.keyin.flight_api.dto.AircraftDtos.*;
import ca.keyin.flight_api.entity.Aircraft;
import ca.keyin.flight_api.repository.AircraftRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/aircraft")
@Transactional
public class AircraftCrudController {

    private final AircraftRepository repo;

    public AircraftCrudController(AircraftRepository repo) { this.repo = repo; }

    @GetMapping
    @Transactional(readOnly = true)
    public List<AircraftResp> list() {
        return repo.findAll().stream()
                .map(a -> new AircraftResp(a.getId(), a.getType(), a.getAirlineName(), a.getNumberOfPassengers()))
                .toList();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public AircraftResp get(@PathVariable Long id) {
        Aircraft a = repo.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Aircraft not found"));
        return new AircraftResp(a.getId(), a.getType(), a.getAirlineName(), a.getNumberOfPassengers());
    }

    @PostMapping
    public AircraftResp create(@RequestBody @Valid CreateOrUpdate in) {
        Aircraft a = new Aircraft();
        a.setType(in.type());
        a.setAirlineName(in.airlineName());
        a.setNumberOfPassengers(in.numberOfPassengers());
        a = repo.save(a);
        return new AircraftResp(a.getId(), a.getType(), a.getAirlineName(), a.getNumberOfPassengers());
    }

    @PutMapping("/{id}")
    public AircraftResp update(@PathVariable Long id, @RequestBody @Valid CreateOrUpdate in) {
        Aircraft a = repo.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Aircraft not found"));
        a.setType(in.type());
        a.setAirlineName(in.airlineName());
        a.setNumberOfPassengers(in.numberOfPassengers());
        a = repo.save(a);
        return new AircraftResp(a.getId(), a.getType(), a.getAirlineName(), a.getNumberOfPassengers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) throw new ResponseStatusException(NOT_FOUND, "Aircraft not found");
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
