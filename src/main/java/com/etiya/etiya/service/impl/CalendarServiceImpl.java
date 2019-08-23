package com.etiya.etiya.service.impl;

import com.etiya.etiya.dto.AirplaneDto;
import com.etiya.etiya.dto.AirportDto;
import com.etiya.etiya.entity.Airplane;
import com.etiya.etiya.entity.Airport;
import com.etiya.etiya.repository.AirplaneRepository;
import com.etiya.etiya.repository.TicketRepository;
import com.etiya.etiya.util.TPage;
import com.etiya.etiya.dto.CalendarDto;
import com.etiya.etiya.entity.Calendar;
import com.etiya.etiya.repository.CalendarRepository;
import com.etiya.etiya.service.CalendarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private CalendarRepository calendarRepository;
    @Autowired
    private AirplaneRepository airplaneRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CalendarServiceImpl(CalendarRepository calendarRepository, AirplaneRepository airplaneRepository,
                               TicketRepository ticketRepository, ModelMapper modelMapper){
        this.calendarRepository = calendarRepository;
        this.airplaneRepository = airplaneRepository;
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<CalendarDto> listeleme(Pageable pageable) {
        Page<Calendar> data = calendarRepository.findAll(pageable);
        TPage tpage = new TPage<CalendarDto>();
        CalendarDto[] dtos = modelMapper.map(data.getContent(), CalendarDto[].class);
        tpage.setStat(data, Arrays.asList(dtos));
        return (Page<CalendarDto>) tpage;
    }

    @Override
    public CalendarDto kayitEkleme(CalendarDto calendarDto) {
        AirplaneDto airplaneDto = calendarDto.getAirplane();
        Long airplaneId = airplaneDto.getId();
        Airplane airplane = airplaneRepository.getOne(airplaneId);
        Integer seatNumber = airplane.getSeatNumber();
        Calendar calendarDb = modelMapper.map(calendarDto, Calendar.class);
        calendarDb.setSeatNumber(seatNumber);
        calendarDb.setSeatFull(seatNumber);
        calendarDb = calendarRepository.save(calendarDb);
        return modelMapper.map(calendarDb, CalendarDto.class);
    }

    @Override
    public CalendarDto kayitBul(Long id) {
        Calendar calendar = calendarRepository.getOne(id);
        return modelMapper.map(calendar, CalendarDto.class);
    }

    @Override
    public Boolean silme(Long id) {
        Calendar calendar = calendarRepository.getOne(id);
        if(calendar.getId().equals(null))
            throw new IllegalArgumentException("Bu id'li kayıt bulunamadı.");
        calendar.setDeparture(null);
        calendar.setAirplane(null);
        calendarRepository.save(calendar);
        calendarRepository.deleteById(id);
        return true;
    }

    @Override
    public CalendarDto guncelleme(Long id, CalendarDto calendarDto) {
        Calendar calendar = calendarRepository.getOne(id);

        AirportDto airportDto = calendarDto.getDeparture();
        Airport airport = modelMapper.map(airportDto,Airport.class);

        AirplaneDto airplaneDto = calendarDto.getAirplane();
        Airplane airplane = modelMapper.map(airplaneDto,Airplane.class);

        if(calendar.getId().equals(null))
            throw new IllegalArgumentException("Bu id'li kayıt bulunamadı.");

        calendar.setFlightTime(calendarDto.getFlightTime());
        calendar.setDeparture(airport);
        calendar.setAirplane(airplane);
        calendar.setDepartureTime(calendarDto.getDepartureTime());
        calendar.setArrivalTime(calendarDto.getArrivalTime());
        calendar.setSeatNumber(calendarDto.getSeatNumber());
        calendar.setSeatFull(calendarDto.getSeatFull());

        calendarRepository.save(calendar);
        return modelMapper.map(calendar, CalendarDto.class);
    }
}
