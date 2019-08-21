package com.etiya.etiya.dto;

import lombok.*;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneDto {

    private Long id;
    private String airplaneName;
    private Integer seatNumber;
    private CompanyDto company;
}


