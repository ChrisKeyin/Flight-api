package ca.keyin.flight_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "airports")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 10)
    private String code;

    // Many airports belong to one city
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
}
