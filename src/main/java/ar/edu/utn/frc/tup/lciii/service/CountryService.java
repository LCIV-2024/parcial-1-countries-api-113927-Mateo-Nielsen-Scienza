package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.model.Language;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {
        private final CountryRepository countryRepository;

        public List<CountryDTO> getAllCountries() {
                return countryRepository.findAll().stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
        }

        public List<CountryDTO> getCountriesByNameOrCode(String name, String code) {
                if (name != null) {
                        return countryRepository.findByNameContainingIgnoreCase(name)
                                .stream()
                                .map(this::convertToDTO)
                                .collect(Collectors.toList());
                } else if (code != null) {
                        return countryRepository.findByCode(code)
                                .stream()
                                .map(this::convertToDTO)
                                .collect(Collectors.toList());
                }
                return getAllCountries();
        }

        public List<CountryDTO> getCountriesByContinent(String continent) {
                return countryRepository.findByRegion(continent)
                        .stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
        }

        public List<CountryDTO> getCountriesByLanguage(String language) {
                return countryRepository.findByLanguages_Key(language)
                        .stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
        }

        public CountryDTO getCountryWithMostBorders() {
                return countryRepository.findAll().stream()
                        .max(Comparator.comparingInt(country ->
                                Optional.ofNullable(country.getBorders()).orElse(Collections.emptyList()).size()))
                        .map(this::convertToDTO)
                        .orElse(null);
        }

        public List<CountryDTO> saveRandomCountries(int amount) {
                List<Country> countries = new ArrayList<>();
                Random random = new Random();

                for (int i = 0; i < amount; i++) {
                        Country country = Country.builder()
                                .name("Country" + (i + 1))
                                .code("C" + (i + 1))
                                .population(random.nextLong(1_000_000_000))
                                .area(random.nextDouble() * 1_000_000)
                                .region("Region" + random.nextInt(5))
                                .borders(Collections.emptyList())
                                .languages(Collections.singletonList(
                                        Language.builder().key("en").name("English").build()))
                                .build();
                        countries.add(country);
                }

                countryRepository.saveAll(countries);
                return countries.stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
        }

        private CountryDTO convertToDTO(Country country) {
                return new CountryDTO(country.getName(), country.getCode());
        }
}