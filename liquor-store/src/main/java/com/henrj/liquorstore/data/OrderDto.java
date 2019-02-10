package com.henrj.liquorstore.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private String customerEmail;
    private List<OrderItemDto> items;
    private LocalDate date;

    public OrderDto(String customerEmail, List<OrderItemDto> items, LocalDate date) {
        this.customerEmail = customerEmail;
        this.items = items;
        this.date = date;
    }
}
