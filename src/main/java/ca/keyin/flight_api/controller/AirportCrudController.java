package ca.keyin.flight_api.controller;

import ca.keyin.flight_api.dto.AirportDtos.*;
import ca.keyin.flight_api.entity.Airport;
import ca.keyin.flight_api.entity.City;
import ca.keyin.flight_api.repository.AirportRepository;
import ca.keyin.flight_api.repository.CityRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/airports")
@Transactional
public class AirportCrudController {

    private final AirportRepository repo;
    private final CityRepository cityRepo;

    public AirportCrudController(AirportRepository repo, CityRepository cityRepo) {
        this.repo = repo; this.cityRepo = cityRepo;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<AirportResp> list() {
        return repo.findAll().stream()
                .map(a -> new AirportResp(a.getId(), a.getName(), a.getCode(), a.getCity().getId()))
                .toList();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public AirportResp get(@PathVariable Long id) {
        Airport a = repo.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Airport not found"));
        return new AirportResp(a.getId(), a.getName(), a.getCode(), a.getCity().getId());
    }

    @PostMapping
    public AirportResp create(@RequestBody @Valid CreateOrUpdate in) {
        City city = cityRepo.findById(in.cityId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "City not found"));
        Airport a = new Airport();
        a.setName(in.name());
        a.setCode(in.code());
        a.setCity(city);
        a = repo.save(a);
        return new AirportResp(a.getId(), a.getName(), a.getCode(), city.getId());
    }

    @PutMapping("/{id}")
    public AirportResp update(@PathVariable Long id, @RequestBody @Valid CreateOrUpdate in) {
        Airport a = repo.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Airport not found"));
        City city = cityRepo.findById(in.cityId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "City not found"));
        a.setName(in.name());
        a.setCode(in.code());
        a.setCity(city);
        a = repo.save(a);
        return new AirportResp(a.getId(), a.getName(), a.getCode(), city.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) throw new ResponseStatusException(NOT_FOUND, "Airport not found");
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
