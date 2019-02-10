package com.henrj.liquorstore.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private Customer customer;
    private List<OrderItem> items;
    private Integer totalCost;
    private LocalDate date;
}
