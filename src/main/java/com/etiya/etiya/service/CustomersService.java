package com.etiya.etiya.service;

import com.etiya.etiya.dto.CustomersDto;

import java.util.List;

public interface CustomersService {

    List<CustomersDto> listeleme();

    CustomersDto kayitEkleme(CustomersDto customersDto);

    CustomersDto kayitBul(Long id);

    Boolean silme(Long id);

    CustomersDto guncelleme(Long id, CustomersDto customersDto);
}
