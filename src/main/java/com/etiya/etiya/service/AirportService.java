package com.etiya.etiya.service;

import com.etiya.etiya.dto.AirportDto;

import java.util.List;

public interface AirportService {

    public List<AirportDto> listeleme();

    AirportDto kayitEkleme(AirportDto airportDto);

    AirportDto kayitBul(Long id);

    Boolean silme(Long id);

    AirportDto guncelleme(Long id, AirportDto airportDto);
}
