package com.etiya.etiya.service.impl;

import com.etiya.etiya.util.TPage;
import com.etiya.etiya.dto.AirplaneDto;
import com.etiya.etiya.entity.Airplane;
import com.etiya.etiya.repository.AirplaneRepository;
import com.etiya.etiya.service.AirplaneService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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
    public Page<AirplaneDto> listeleme(Pageable pageable) {
        Page<Airplane> data = airplaneRepository.findAll(pageable);
        TPage tpage = new TPage<AirplaneDto>();
        AirplaneDto[] dtos = modelMapper.map(data.getContent(),AirplaneDto[].class);
        tpage.setStat(data, Arrays.asList(dtos));
        return (Page<AirplaneDto>) tpage;
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
        //airplane.setCompany(airplaneDto.getCompany());
        airplaneRepository.save(airplane);
        return modelMapper.map(airplane,AirplaneDto.class);
    }
}
