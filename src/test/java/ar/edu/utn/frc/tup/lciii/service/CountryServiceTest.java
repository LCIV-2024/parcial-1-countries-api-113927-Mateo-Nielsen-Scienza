package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.model.Language;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCountries() {
        List<Country> countries = new ArrayList<>();
        countries.add(new Country(1L, "Argentina", "AR", 45000000L, 2780000.0, "South America", Collections.emptyList(), Collections.emptyList()));
        countries.add(new Country(2L, "Brazil", "BR", 211000000L, 8516000.0, "South America", Collections.emptyList(), Collections.emptyList()));

        when(countryRepository.findAll()).thenReturn(countries);

        List<CountryDTO> result = countryService.getAllCountries();

        assertEquals(2, result.size());
        assertEquals("Argentina", result.get(0).getName());
        assertEquals("AR", result.get(0).getCode());
    }

    @Test
    void testGetCountriesByNameOrCode() {
        List<Country> countries = new ArrayList<>();
        countries.add(new Country(1L, "Argentina", "AR", null, null, null, null, null));

        when(countryRepository.findByNameContainingIgnoreCase("Argentina")).thenReturn(countries);

        List<CountryDTO> result = countryService.getCountriesByNameOrCode("Argentina", null);

        assertEquals(1, result.size());
        assertEquals("Argentina", result.get(0).getName());
    }

    @Test
    void testGetCountriesByContinent() {
        List<Country> countries = new ArrayList<>();
        countries.add(new Country(1L, "Argentina", "AR", null, null, "South America", null, null));

        when(countryRepository.findByRegion("South America")).thenReturn(countries);

        List<CountryDTO> result = countryService.getCountriesByContinent("South America");

        assertEquals(1, result.size());
        assertEquals("Argentina", result.get(0).getName());
    }

    @Test
    void testGetCountriesByLanguage() {
        List<Country> countries = new ArrayList<>();
        countries.add(new Country(1L, "Argentina", "AR", null, null, null, null, Collections.singletonList(new Language(1L, "es", "Spanish", null))));

        when(countryRepository.findByLanguages_Key("es")).thenReturn(countries);

        List<CountryDTO> result = countryService.getCountriesByLanguage("es");

        assertEquals(1, result.size());
        assertEquals("Argentina", result.get(0).getName());
    }

    @Test
    void testGetCountryWithMostBorders() {
        List<Country> countries = new ArrayList<>();
        countries.add(new Country(1L, "Argentina", "AR", null, null, null, List.of("BR", "PY"), null));
        countries.add(new Country(2L, "Brazil", "BR", null, null, null, List.of("AR"), null));

        when(countryRepository.findAll()).thenReturn(countries);

        CountryDTO result = countryService.getCountryWithMostBorders();

        assertNotNull(result);
        assertEquals("Argentina", result.getName());
    }

    @Test
    void testSaveRandomCountries() {
        when(countryRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

        List<CountryDTO> result = countryService.saveRandomCountries(5);

        assertNotNull(result);
        assertEquals(5, result.size());
    }
}