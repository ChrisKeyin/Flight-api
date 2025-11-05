package ca.keyin.flight_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "aircraft")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String type;

    @Column(nullable = false, length = 150)
    private String airlineName;

    @Column(nullable = false)
    private int numberOfPassengers;

    @ManyToMany(mappedBy = "aircraftList")
    @Builder.Default
    private List<Passenger> passengers = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "aircraft_airports",
            joinColumns = @JoinColumn(name = "aircraft_id"),
            inverseJoinColumns = @JoinColumn(name = "airport_id")
    )
    @Builder.Default
    private List<Airport> airports = new ArrayList<>();
}
