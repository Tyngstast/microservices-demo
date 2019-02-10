package com.henrj.beerservice.beer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeerRepository extends JpaRepository<Beer, Long> {
    Optional<Beer> findByBreweryAndName(String brewery, String name);
}
