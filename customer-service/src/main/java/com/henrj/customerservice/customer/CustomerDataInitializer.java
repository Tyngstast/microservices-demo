package com.henrj.customerservice.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerDataInitializer {

    @Bean
    public CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            if (customerRepository.findAll().isEmpty()) {
                customerRepository.save(new Customer("Hank", "hank@mail.com"));
                customerRepository.save(new Customer("Jesse", "jesse@mail.com"));
                customerRepository.save(new Customer("Walter", "walter@mail.com"));
                customerRepository.save(new Customer("User Usersson", "user@mail.com"));
            }
            customerRepository.findAll().forEach(c -> log.info(c.toString()));
        };
    }
}
