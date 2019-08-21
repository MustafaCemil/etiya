package com.etiya.etiya.service.impl;

import com.etiya.etiya.Util.TPage;
import com.etiya.etiya.dto.AirplaneDto;
import com.etiya.etiya.dto.AirportDto;
import com.etiya.etiya.entity.Airport;
import com.etiya.etiya.repository.AirportRepository;
import com.etiya.etiya.service.AirportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AirportServiceImpl implements AirportService {

    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private ModelMapper modelMapper;

    public AirportServiceImpl(AirportRepository airportRepository, ModelMapper modelMapper){
        this.airportRepository = airportRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<AirportDto> listeleme(Pageable pageable) {
        Page<Airport> data = airportRepository.findAll(pageable);
        TPage tpage = new TPage<AirportDto>();
        AirportDto[] dtos = modelMapper.map(data.getContent(),AirportDto[].class);
        tpage.setStat(data, Arrays.asList(dtos));
        return (Page<AirportDto>) tpage;
    }

    @Override
    public AirportDto kayitEkleme(AirportDto airportDto) {
        Airport airportDb = modelMapper.map(airportDto, Airport.class);
        airportDb = airportRepository.save(airportDb);
        return modelMapper.map(airportDb, AirportDto.class);
    }

    @Override
    public AirportDto kayitBul(Long id) {
        Airport airportId = airportRepository.getOne(id);
        return modelMapper.map(airportId,AirportDto.class);
    }

    @Override
    public Boolean silme(Long id) {
        Airport airport = airportRepository.getOne(id);
        if(airport.getId().equals(null))
            throw new IllegalArgumentException("Bu id'li kayıt bulunamadı.");
        airportRepository.deleteById(id);
        return true;
    }

    @Override
    public AirportDto guncelleme(Long id, AirportDto airportDto) {
        Airport airport = airportRepository.getOne(id);
        if(airport.getId().equals(null))
            throw new IllegalArgumentException("Bu id'li kayıt bulunamadı.");

        airport.setAirportName(airportDto.getAirportName());
        airport.setAirportCity(airportDto.getAirportCity());
        airport.setAirportCountry(airportDto.getAirportCountry());

        airportRepository.save(airport);
        return modelMapper.map(airport,AirportDto.class);
    }
}
