package com.etiya.etiya.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "airport")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Airport extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "airport_name")
    private String airportName;

    @Column(name = "airport_city")
    private String airportCity;

    @Column(name = "airport_country")
    private String airportCountry;

    @OneToMany(mappedBy = "departure",fetch = FetchType.LAZY)
    private Set<Calendar> departureAirport;

}
