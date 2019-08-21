package com.etiya.etiya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CustomersDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private Date birthDay;
    private String phoneNumber;
    private Set<TicketDto> ticketDto;

}
