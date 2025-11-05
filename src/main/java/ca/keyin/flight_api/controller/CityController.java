package ca.keyin.flight_api.controller;

import ca.keyin.flight_api.entity.Airport;
import ca.keyin.flight_api.entity.City;
import ca.keyin.flight_api.repository.AirportRepository;
import ca.keyin.flight_api.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;
    private final AirportRepository airportRepository;

    public CityController(CityService cityService,
                          AirportRepository airportRepository) {
        this.cityService = cityService;
        this.airportRepository = airportRepository;
    }


    @PostMapping
    public ResponseEntity<City> create(@RequestBody City city) {
        return ResponseEntity.ok(cityService.create(city));
    }

    @GetMapping
    public ResponseEntity<List<City>> findAll() {
        return ResponseEntity.ok(cityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> update(@PathVariable Long id, @RequestBody City city) {
        return ResponseEntity.ok(cityService.update(id, city));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cityService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/airports")
    public ResponseEntity<List<Airport>> getAirportsForCity(@PathVariable Long id) {
        List<Airport> airports = airportRepository.findByCityId(id);
        return ResponseEntity.ok(airports);
    }
}
