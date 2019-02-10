package com.henrj.liquorstore.service;

import com.henrj.liquorstore.data.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll();
    Customer getByEmail(String email);
}
