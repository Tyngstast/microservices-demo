package com.henrj.liquorstore.web;

import com.henrj.liquorstore.data.Beer;
import com.henrj.liquorstore.data.Customer;
import com.henrj.liquorstore.data.Order;
import com.henrj.liquorstore.data.OrderDto;
import com.henrj.liquorstore.service.BeerService;
import com.henrj.liquorstore.service.CustomerService;
import com.henrj.liquorstore.service.OrderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LiquorStoreController {


    private final CustomerService customerService;
    private final OrderService orderService;
    private final BeerService beerService;

    public LiquorStoreController(CustomerService customerService, OrderService orderService, BeerService beerService) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.beerService = beerService;
    }

    @GetMapping("/beers")
    public ResponseEntity getBeers() {
        List<Beer> beers = this.beerService.getAll();
        return ResponseEntity.ok(beers);
    }

    @GetMapping("/beers/{id}")
    public ResponseEntity getBeerById(@PathVariable Long id) {
        Beer beer = this.beerService.getById(id);
        return ResponseEntity.ok(beer);
    }

    @GetMapping("/customers")
    public ResponseEntity getCustomers() {
        List<Customer> customers = this.customerService.getAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping(value = "/customers", params = "customerEmail")
    public ResponseEntity getCustomers(@RequestParam String customerEmail) {
        Customer customer = this.customerService.getByEmail(customerEmail);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/orders")
    public ResponseEntity getOrders() {
        List<Order> orders = this.orderService.getAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity getOrderById(@PathVariable Long id) {
        Order order = this.orderService.getById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping(value = "/orders", params = "customerEmail")
    public ResponseEntity getOrderByCustomerEmail(@RequestParam String customerEmail) {
        List<Order> orders = this.orderService.getByCustomerEmail(customerEmail);
        return ResponseEntity.ok(orders);
    }

    @GetMapping(value = "/user")
    public ResponseEntity getUser(Principal principal) {
        return ResponseEntity.ok(principal);
    }

    @PostMapping("/orders")
    public ResponseEntity createOrder(@Valid @RequestBody OrderDto orderDto) {
        URI location = this.orderService.create(orderDto);
        return ResponseEntity.created(location).build();
    }
}
