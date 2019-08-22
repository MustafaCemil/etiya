package com.etiya.etiya.service.impl;

import com.etiya.etiya.dto.AirplaneDto;
import com.etiya.etiya.dto.AirportDto;
import com.etiya.etiya.entity.Airplane;
import com.etiya.etiya.entity.Airport;
import com.etiya.etiya.repository.AirplaneRepository;
import com.etiya.etiya.repository.TicketRepository;
import com.etiya.etiya.util.TPage;
import com.etiya.etiya.dto.CleanderDto;
import com.etiya.etiya.entity.Cleander;
import com.etiya.etiya.repository.CleanderRepository;
import com.etiya.etiya.service.CleanderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CleanderServiceImpl implements CleanderService {

    @Autowired
    private CleanderRepository cleanderRepository;
    @Autowired
    private AirplaneRepository airplaneRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CleanderServiceImpl(CleanderRepository cleanderRepository, AirplaneRepository airplaneRepository,
                               TicketRepository ticketRepository, ModelMapper modelMapper){
        this.cleanderRepository = cleanderRepository;
        this.airplaneRepository = airplaneRepository;
        this.ticketRepository = ticketRepository;
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
        AirplaneDto airplaneDto = cleanderDto.getAirplane();
        Long airplaneId = airplaneDto.getId();
        Airplane airplane = airplaneRepository.getOne(airplaneId);
        Integer seatNumber = airplane.getSeatNumber();
        Cleander cleanderDb = modelMapper.map(cleanderDto, Cleander.class);
        cleanderDb.setSeatNumber(seatNumber);
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

        AirportDto airportDto = cleanderDto.getDeparture();
        Airport airport = modelMapper.map(airportDto,Airport.class);

        AirplaneDto airplaneDto = cleanderDto.getAirplane();
        Airplane airplane = modelMapper.map(airplaneDto,Airplane.class);

        if(cleander.getId().equals(null))
            throw new IllegalArgumentException("Bu id'li kayıt bulunamadı.");

        cleander.setFlightTime(cleanderDto.getFlightTime());
        cleander.setDeparture(airport);
        cleander.setAirplane(airplane);
        cleander.setDepartureTime(cleanderDto.getDepartureTime());
        cleander.setArrivalTime(cleanderDto.getArrivalTime());

        cleanderRepository.save(cleander);
        return modelMapper.map(cleander,CleanderDto.class);
    }
}
