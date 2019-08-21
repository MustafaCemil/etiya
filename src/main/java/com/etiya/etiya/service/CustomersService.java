package com.etiya.etiya.service;

import com.etiya.etiya.dto.CustomersDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomersService {

    Page<CustomersDto> listeleme(Pageable pageable);

    CustomersDto kayitEkleme(CustomersDto customersDto);

    CustomersDto kayitBul(Long id);

    Boolean silme(Long id);

    CustomersDto guncelleme(Long id, CustomersDto customersDto);
}
