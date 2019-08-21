package com.etiya.etiya.service;

import com.etiya.etiya.dto.AirportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirportService {

    Page<AirportDto> listeleme(Pageable pageable);

    AirportDto kayitEkleme(AirportDto airportDto);

    AirportDto kayitBul(Long id);

    Boolean silme(Long id);

    AirportDto guncelleme(Long id, AirportDto airportDto);
}
