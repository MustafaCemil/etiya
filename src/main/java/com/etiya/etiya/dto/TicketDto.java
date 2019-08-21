package com.etiya.etiya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {

    private Long id;
    private CustomersDto customersDto;
    private CleanderDto cleanderDto;
    private String seatNumber;
    private String pnr;
    private Float price;
}
