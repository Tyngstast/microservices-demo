package com.henrj.orderservice.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
@Slf4j
public class OrderDataInitializer implements CommandLineRunner {

    private final OrderRepository orderRepository;

    public OrderDataInitializer(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (orderRepository.findAll().isEmpty()) {
            log.info("** Is Empty, seeding...");

            String hankEmail = "hank@mail.com";
            String jesseEmail = "jesse@mail.com";
            String walterEmail = "walter@mail.com";
            String userEmail = "user@mail.com";

            Long amazingHazeId = 1L;
            Long fatamorganaId = 2L;
            Long duggesId = 3L;
            Long falconId = 4L;
            Long mariestadsId = 5L;

            orderRepository.save(new Order(1L,
                    userEmail,
                    Arrays.asList(
                            new OrderItem(amazingHazeId, 2L),
                            new OrderItem(fatamorganaId, 1L),
                            new OrderItem(duggesId, 3L)),
                    LocalDate.now()));
            orderRepository.save(new Order(2L,
                    userEmail,
                    Arrays.asList(
                            new OrderItem(falconId, 10L),
                            new OrderItem(mariestadsId, 10L)),
                    LocalDate.now()));
            orderRepository.save(new Order(3L,
                    walterEmail,
                    Arrays.asList(
                            new OrderItem(falconId, 24L)),
                    LocalDate.now()));
        }

        orderRepository.findAll().forEach(o -> log.info(o.toString()));
    }
}
