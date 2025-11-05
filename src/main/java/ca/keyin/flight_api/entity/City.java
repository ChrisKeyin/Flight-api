package ca.keyin.flight_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cities")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 80)
    private String state;

    @Column(nullable = false)
    private int population;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Airport> airports;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Passenger> passengers;

}
