package com.henrj.beerservice.beer;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/beers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BeerController {

    private final BeerRepository beerRepository;

    public BeerController(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @GetMapping
    public List<Beer> getAll() {
        return beerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        Optional<Beer> beer = beerRepository.findById(id);
        return beer
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = {"brewery", "name"})
    public ResponseEntity getByBreweryAndName(@RequestParam String brewery, @RequestParam String name) {
        Optional<Beer> beer = beerRepository.findByBreweryAndName(brewery, name);
        return beer
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid Beer beer) {
        if (!beerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        beer.setId(id);
        beerRepository.save(beer);
        return ResponseEntity.ok(beer);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid Beer beer) {
        if (beerRepository.findByBreweryAndName(beer.getBrewery(), beer.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Beer saved = beerRepository.save(beer);
        return ResponseEntity
                .created(URI.create("/beers/" + saved.getId()))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (!beerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        beerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
