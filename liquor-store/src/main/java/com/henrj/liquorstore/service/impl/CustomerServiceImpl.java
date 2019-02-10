package com.henrj.liquorstore.service.impl;

import com.henrj.liquorstore.data.Customer;
import com.henrj.liquorstore.service.AbstractRestService;
import com.henrj.liquorstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@Component
public class CustomerServiceImpl extends AbstractRestService implements CustomerService {

    @Value("${CUSTOMERS_URL}")
    private String customersUrl;

    private final WebClient webClient;

    public CustomerServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<Customer> getAll() {
        ParameterizedTypeReference<List<Customer>> typeReference = new ParameterizedTypeReference<List<Customer>>() {};
        return this.webClient
                .get()
                .uri(customersUrl)
                .retrieve()
                .bodyToMono(typeReference)
                .block();
    }

    @Override
    public Customer getByEmail(String email) {
        return this.webClient
                .get()
                .uri(buildUri(customersUrl, Collections.singletonMap("email", email)))
                .retrieve()
                .bodyToMono(Customer.class)
                .block();
    }
}
