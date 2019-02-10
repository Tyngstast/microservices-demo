package com.henrj.liquorstore.service.impl;

import com.henrj.liquorstore.data.Beer;
import com.henrj.liquorstore.service.AbstractRestService;
import com.henrj.liquorstore.service.BeerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BeerServiceImpl extends AbstractRestService implements BeerService {

    @Value("${BEERS_URL}")
    private String beersUrl;

    private final WebClient webClient;

    public BeerServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<Beer> getAll() {
        ParameterizedTypeReference<List<Beer>> typeReference = new ParameterizedTypeReference<List<Beer>>() {};
        return this.webClient
                .get()
                .uri(beersUrl)
                .retrieve()
                .bodyToMono(typeReference)
                .block();
    }

    @Override
    public Beer getById(Long id) {
        return this.webClient
                .get()
                .uri(beersUrl + id)
                .retrieve()
                .bodyToMono(Beer.class)
                .block();
    }

    @Override
    public Beer getByBreweryAndName(String brewery, String name) {
        Map<String, String> params = new HashMap<>();
        params.put("brewery", brewery);
        params.put("name", name);
        return this.webClient
                .get()
                .uri(buildUri(beersUrl, params))
                .retrieve()
                .bodyToMono(Beer.class)
                .block();
    }
}
