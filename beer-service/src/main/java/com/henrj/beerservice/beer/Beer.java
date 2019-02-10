package com.henrj.beerservice.beer;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "brewery"})
})
public class Beer extends Beverage {
    @Id
    @GeneratedValue
    private Long id;
    private String brewery;

    public Beer(String brewery, String name, Integer price) {
        super(name, price);
        this.brewery = brewery;
    }

    public Beer(Long id, String brewery, String name, Integer price) {
        super(name, price);
        this.id = id;
        this.brewery = brewery;
    }
}
