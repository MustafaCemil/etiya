package com.etiya.etiya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AirportDto {

    private Long id;
    private String airportName;
    private String airportCity;
    private String airportCountry;
    private Set<CleanderDto> departureAirport;
}
