package com.henrj.liquorstore.service;

import com.henrj.liquorstore.data.Beer;

import java.util.List;

public interface BeerService {
    List<Beer> getAll();
    Beer getById(Long id);
    Beer getByBreweryAndName(String brewery, String name);
}
