package com.henrj.liquorstore.service.impl;

import com.henrj.liquorstore.data.*;
import com.henrj.liquorstore.exception.NotFoundException;
import com.henrj.liquorstore.service.AbstractRestService;
import com.henrj.liquorstore.service.BeerService;
import com.henrj.liquorstore.service.CustomerService;
import com.henrj.liquorstore.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OrderServiceImpl extends AbstractRestService implements OrderService {

    @Value("${ORDERS_URL}")
    private String ordersUrl;

    private final WebClient webClient;
    private final CustomerService customerService;
    private final BeerService beerService;

    public OrderServiceImpl(WebClient webClient, CustomerService customerService, BeerService beerService) {
        this.webClient = webClient;
        this.customerService = customerService;
        this.beerService = beerService;
    }

    @Override
    public List<Order> getAll() {
        ParameterizedTypeReference<List<OrderDto>> typeReference = new ParameterizedTypeReference<List<OrderDto>>() {};
        List<OrderDto> orderDtos = this.webClient
                .get()
                .uri(ordersUrl)
                .retrieve()
                .bodyToMono(typeReference)
                .block();

        return orderDtos.stream()
                .map(this::mapToOrder)
                .collect(Collectors.toList());
    }

    @Override
    public Order getById(Long id) {
        OrderDto orderDto = this.webClient
                .get()
                .uri(ordersUrl + id)
                .retrieve()
                .bodyToMono(OrderDto.class)
                .block();

        return mapToOrder(orderDto);
    }

    @Override
    public List<Order> getByCustomerEmail(String email) {
        ParameterizedTypeReference<List<OrderDto>> typeReference = new ParameterizedTypeReference<List<OrderDto>>() {};
        List<OrderDto> orderDtos = this.webClient
                .get()
                .uri(buildUri(ordersUrl, Collections.singletonMap("customerEmail", email)))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    throw new ResponseStatusException(response.statusCode());
                })
                .bodyToMono(typeReference)
                .block();

        return orderDtos.stream()
                .map(this::mapToOrder)
                .collect(Collectors.toList());
    }

    @Override
    public URI create(OrderDto orderDto) {
        String email = orderDto.getCustomerEmail();
        try {
            this.customerService.getByEmail(email);
        } catch (WebClientResponseException.NotFound e) {
            throw new NotFoundException("Customer with email: " + email + " not found.");
        }

        orderDto.setDate(LocalDate.now());

        ClientResponse response = this.webClient
                .post()
                .uri(ordersUrl)
                .body(BodyInserters.fromObject(orderDto))
                .exchange()
                .block();

        return response.headers().asHttpHeaders().getLocation();
    }

    private Order mapToOrder(OrderDto orderDto) {
        Customer customer = this.customerService.getByEmail(orderDto.getCustomerEmail());
        List<OrderItem> items = new ArrayList<>();
        int totalCost = 0;
        for (OrderItemDto item : orderDto.getItems()) {
            Beer beer = this.beerService.getById(item.getItem());
            items.add(new OrderItem(beer, item.getCount()));
            totalCost += (beer.getPrice() * item.getCount());
        }
        return new Order(orderDto.getId(), customer, items, totalCost, orderDto.getDate());
    }

}
