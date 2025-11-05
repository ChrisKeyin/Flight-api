package ca.keyin.flight_api.controller;

import ca.keyin.flight_api.dto.CityDtos.*;
import ca.keyin.flight_api.entity.City;
import ca.keyin.flight_api.repository.CityRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/admin/cities")
@Transactional
public class CityCrudController {

    private final CityRepository repo;

    public CityCrudController(CityRepository repo) { this.repo = repo; }

    @GetMapping
    @Transactional(readOnly = true)
    public List<CityResp> list() {
        return repo.findAll().stream()
                .map(c -> new CityResp(c.getId(), c.getName(), c.getState(), c.getPopulation()))
                .toList();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public CityResp get(@PathVariable Long id) {
        City c = repo.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "City not found"));
        return new CityResp(c.getId(), c.getName(), c.getState(), c.getPopulation());
    }

    @PostMapping
    public CityResp create(@RequestBody @Valid CreateOrUpdate in) {
        City c = new City();
        c.setName(in.name());
        c.setState(in.state());
        c.setPopulation(in.population());
        c = repo.save(c);
        return new CityResp(c.getId(), c.getName(), c.getState(), c.getPopulation());
    }

    @PutMapping("/{id}")
    public CityResp update(@PathVariable Long id, @RequestBody @Valid CreateOrUpdate in) {
        City c = repo.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "City not found"));
        c.setName(in.name());
        c.setState(in.state());
        c.setPopulation(in.population());
        c = repo.save(c);
        return new CityResp(c.getId(), c.getName(), c.getState(), c.getPopulation());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) throw new ResponseStatusException(NOT_FOUND, "City not found");
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
