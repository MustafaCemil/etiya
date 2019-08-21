package com.etiya.etiya.service.impl;

import com.etiya.etiya.dto.AirplaneDto;
import com.etiya.etiya.dto.CompanyDto;
import com.etiya.etiya.entity.Airplane;
import com.etiya.etiya.entity.Company;
import com.etiya.etiya.repository.AirplaneRepository;
import com.etiya.etiya.repository.CompanyRepository;
import com.etiya.etiya.service.AirplaneService;
import com.etiya.etiya.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class AirplaneServiceImpl implements AirplaneService {

    @Autowired
    private AirplaneRepository airplaneRepository;
    @Autowired
    private ModelMapper modelMapper;

    public AirplaneServiceImpl(AirplaneRepository airplaneRepository, ModelMapper modelMapper){
        this.airplaneRepository = airplaneRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AirplaneDto> listeleme() {
        List<Airplane> airplane = airplaneRepository.findAll();
        List<AirplaneDto> airplaneDto = new ArrayList<>();
        for(Airplane a:airplane){
            airplaneDto.add(modelMapper.map(a,AirplaneDto.class));
        }
        return airplaneDto;
    }

    @Override
    public AirplaneDto kayitEkleme(AirplaneDto airplaneDto) {
        Airplane airplaneDb = modelMapper.map(airplaneDto, Airplane.class);
        airplaneDb = airplaneRepository.save(airplaneDb);
        return modelMapper.map(airplaneDb, AirplaneDto.class);
    }

    @Override
    public AirplaneDto kayitBul(Long id) {
        Airplane airplaneId = airplaneRepository.getOne(id);
        return modelMapper.map(airplaneId,AirplaneDto.class);
    }

    @Override
    public Boolean silme(Long id) {
        Airplane airplane = airplaneRepository.getOne(id);
        if(airplane.getId().equals(null))
            throw new IllegalArgumentException("Bu id'li kayıt bulunamadı.");
        airplane.setCompany(null);
        airplaneRepository.save(airplane);
        airplaneRepository.deleteById(id);
        return true;
    }

    @Override
    public AirplaneDto guncelleme(Long id, AirplaneDto airplaneDto) {
        Airplane airplane = airplaneRepository.getOne(id);
        if(airplane.getId().equals(null))
            throw new IllegalArgumentException("Bu id'li kayıt bulunamadı.");

        airplane.setAirplaneName(airplaneDto.getAirplaneName());
        airplane.setSeatNumber(airplaneDto.getSeatNumber());
        airplane.setCompany(airplaneDto.getCompany());

        airplaneRepository.save(airplane);
        return modelMapper.map(airplane,AirplaneDto.class);
    }
}
