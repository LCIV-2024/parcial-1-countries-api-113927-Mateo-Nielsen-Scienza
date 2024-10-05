package ar.edu.utn.frc.tup.lciii.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "countries")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String code;

    private Long population;

    private Double area;

    private String region;

    @ElementCollection
    @CollectionTable(name = "country_borders",
            joinColumns = @JoinColumn(name = "country_id"))
    @Column(name = "border")
    private List<String> borders;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Language> languages;
}