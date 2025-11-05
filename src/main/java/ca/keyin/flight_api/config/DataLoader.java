package ca.keyin.flight_api.config;

import ca.keyin.flight_api.entity.*;
import ca.keyin.flight_api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final CityRepository cityRepository;
    private final AirportRepository airportRepository;
    private final PassengerRepository passengerRepository;
    private final AircraftRepository aircraftRepository;

    public DataLoader(CityRepository cityRepository,
                      AirportRepository airportRepository,
                      PassengerRepository passengerRepository,
                      AircraftRepository aircraftRepository) {
        this.cityRepository = cityRepository;
        this.airportRepository = airportRepository;
        this.passengerRepository = passengerRepository;
        this.aircraftRepository = aircraftRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        if (cityRepository.count() > 0 || airportRepository.count() > 0
                || passengerRepository.count() > 0 || aircraftRepository.count() > 0) {
            return;
        }

        City boston = City.builder().name("Boston").state("MA").population(675000).build();
        City toronto = City.builder().name("Toronto").state("ON").population(2790000).build();
        City vancouver = City.builder().name("Vancouver").state("BC").population(675218).build();

        cityRepository.saveAll(List.of(boston, toronto, vancouver));

        Airport bos = Airport.builder().name("Logan International Airport").code("BOS").city(boston).build();
        Airport yyz = Airport.builder().name("Toronto Pearson International Airport").code("YYZ").city(toronto).build();
        Airport yvr = Airport.builder().name("Vancouver International Airport").code("YVR").city(vancouver).build();

        airportRepository.saveAll(List.of(bos, yyz, yvr));

        Aircraft a320_delta = Aircraft.builder().type("Airbus A320").airlineName("Delta").numberOfPassengers(180).build();
        Aircraft b737_wj   = Aircraft.builder().type("Boeing 737-800").airlineName("WestJet").numberOfPassengers(174).build();
        Aircraft a220_ac   = Aircraft.builder().type("Airbus A220").airlineName("Air Canada").numberOfPassengers(137).build();

        a320_delta.getAirports().addAll(List.of(bos, yyz));
        b737_wj.getAirports().addAll(List.of(yyz, yvr));
        a220_ac.getAirports().addAll(List.of(bos, yvr));

        aircraftRepository.saveAll(List.of(a320_delta, b737_wj, a220_ac));

        Passenger alice = Passenger.builder().firstName("Alice").lastName("Ng").phoneNumber("555-1111").city(toronto).build();
        Passenger bob   = Passenger.builder().firstName("Bob").lastName("Lee").phoneNumber("555-2222").city(boston).build();
        Passenger chris = Passenger.builder().firstName("Chris").lastName("King").phoneNumber("555-3333").city(vancouver).build();

        alice.getAircraftList().addAll(List.of(a320_delta, b737_wj));
        bob.getAircraftList().addAll(List.of(a320_delta, a220_ac));
        chris.getAircraftList().addAll(List.of(b737_wj, a220_ac));

        passengerRepository.saveAll(List.of(alice, bob, chris));
    }
}
