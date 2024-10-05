// src/main/java/ar/edu/utn/frc/tup/lciii/model/Language.java
package ar.edu.utn.frc.tup.lciii.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "languages")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;

    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}