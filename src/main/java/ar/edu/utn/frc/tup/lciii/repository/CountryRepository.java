package ar.edu.utn.frc.tup.lciii.repository;

import ar.edu.utn.frc.tup.lciii.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Long> {
    List<Country> findByNameContainingIgnoreCase(String name);
    List<Country> findByCode(String code);
    List<Country> findByRegion(String region);
    List<Country> findByLanguages_Key(String key);
}