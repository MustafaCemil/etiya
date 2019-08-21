package com.etiya.etiya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CleanderDto {

    private Long id;
    private Date flightTime;
    private AirportDto departureDto;
    private AirplaneDto airplaneDto;
    private Date departureTime;
    private Date arrivalTime;
    private Set<TicketDto> ticketDto;
}
