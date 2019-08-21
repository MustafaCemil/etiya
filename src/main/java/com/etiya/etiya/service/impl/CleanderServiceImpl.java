package com.etiya.etiya.service.impl;

import com.etiya.etiya.Util.TPage;
import com.etiya.etiya.dto.CleanderDto;
import com.etiya.etiya.entity.Cleander;
import com.etiya.etiya.repository.CleanderRepository;
import com.etiya.etiya.service.CleanderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CleanderServiceImpl implements CleanderService {

    @Autowired
    private CleanderRepository cleanderRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CleanderServiceImpl(CleanderRepository cleanderRepository, ModelMapper modelMapper){
        this.cleanderRepository = cleanderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<CleanderDto> listeleme(Pageable pageable) {
        Page<Cleander> data = cleanderRepository.findAll(pageable);
        TPage tpage = new TPage<CleanderDto>();
        CleanderDto[] dtos = modelMapper.map(data.getContent(),CleanderDto[].class);
        tpage.setStat(data, Arrays.asList(dtos));
        return (Page<CleanderDto>) tpage;
    }

    @Override
    public CleanderDto kayitEkleme(CleanderDto cleanderDto) {
        Cleander cleanderDb = modelMapper.map(cleanderDto, Cleander.class);
        cleanderDb = cleanderRepository.save(cleanderDb);
        return modelMapper.map(cleanderDb, CleanderDto.class);
    }

    @Override
    public CleanderDto kayitBul(Long id) {
        Cleander cleander = cleanderRepository.getOne(id);
        return modelMapper.map(cleander,CleanderDto.class);
    }

    @Override
    public Boolean silme(Long id) {
        Cleander cleander = cleanderRepository.getOne(id);
        if(cleander.getId().equals(null))
            throw new IllegalArgumentException("Bu id'li kayıt bulunamadı.");
        cleander.setDeparture(null);
        cleander.setAirplane(null);
        cleanderRepository.save(cleander);
        cleanderRepository.deleteById(id);
        return true;
    }

    @Override
    public CleanderDto guncelleme(Long id, CleanderDto cleanderDto) {
        Cleander cleander = cleanderRepository.getOne(id);
        if(cleander.getId().equals(null))
            throw new IllegalArgumentException("Bu id'li kayıt bulunamadı.");

        cleander.setFlightTime(cleanderDto.getFlightTime());
        //cleander.setArrival(cleanderDto.getArrivalDto());
        //cleander.setDeparture(cleanderDto.getDepartureDto());
        //cleander.setAirplane(cleanderDto.getAirplaneDto());
        //cleander.setDepartureTime(cleanderDto.getDepartureTime());
        //cleander.setArrivalTime(cleanderDto.getArrivalTime());

        cleanderRepository.save(cleander);
        return modelMapper.map(cleander,CleanderDto.class);
    }
}
