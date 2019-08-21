package com.etiya.etiya.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty
    private String airportName;

    @Column(name = "airport_city")
    @NotEmpty
    private String airportCity;

    @Column(name = "airport_country")
    @NotEmpty
    private String airportCountry;

    @OneToMany(mappedBy = "departure")
    private Set<Cleander> departureAirport;

}
