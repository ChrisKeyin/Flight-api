package ca.keyin.flight_api.service;

import ca.keyin.flight_api.entity.City;
import ca.keyin.flight_api.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City create(City city) {
        return cityRepository.save(city);
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public City findById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("City not found: " + id));
    }

    public City update(Long id, City updated) {
        City existing = findById(id);
        existing.setName(updated.getName());
        existing.setState(updated.getState());
        existing.setPopulation(updated.getPopulation());
        return cityRepository.save(existing);
    }

    public void delete(Long id) {
        cityRepository.deleteById(id);
    }
}
