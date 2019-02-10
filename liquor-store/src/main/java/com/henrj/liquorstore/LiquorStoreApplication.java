package com.henrj.liquorstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LiquorStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiquorStoreApplication.class, args);
    }
}
