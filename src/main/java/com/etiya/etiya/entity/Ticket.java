package com.etiya.etiya.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "flights")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Ticket extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customers_id")
    private Customers customers;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cleander_id")
    private Cleander cleander;

    @Column(name = "seat_number")
    @NotEmpty
    private String seatNumber;

    @Column(name = "pnr")
    @NotEmpty
    private String pnr;

    @Column(name = "price")
    @NotEmpty
    private Float price;
}
