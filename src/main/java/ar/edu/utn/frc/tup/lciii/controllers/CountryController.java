package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.SaveCountriesRequest;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryDTO>> getCountries(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code) {
        return ResponseEntity.ok(countryService.getCountriesByNameOrCode(name, code));
    }

    @GetMapping("/{continent}/continent")
    public ResponseEntity<List<CountryDTO>> getCountriesByContinent(
            @PathVariable String continent) {
        return ResponseEntity.ok(countryService.getCountriesByContinent(continent));
    }

    @GetMapping("/{language}/language")
    public ResponseEntity<List<CountryDTO>> getCountriesByLanguage(
            @PathVariable String language) {
        return ResponseEntity.ok(countryService.getCountriesByLanguage(language));
    }

    @GetMapping("/most-borders")
    public ResponseEntity<CountryDTO> getCountryWithMostBorders() {
        return ResponseEntity.ok(countryService.getCountryWithMostBorders());
    }

    @PostMapping
    public ResponseEntity<List<CountryDTO>> saveCountries(@RequestBody SaveCountriesRequest request) {
        return ResponseEntity.ok(countryService.saveRandomCountries(request.getAmountOfCountryToSave()));
    }
}
