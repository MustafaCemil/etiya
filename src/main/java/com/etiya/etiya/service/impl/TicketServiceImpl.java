package com.etiya.etiya.service.impl;

import com.etiya.etiya.dto.TicketDto;
import com.etiya.etiya.entity.Ticket;
import com.etiya.etiya.repository.TicketRepository;
import com.etiya.etiya.service.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;

    public TicketServiceImpl(TicketRepository ticketRepository, ModelMapper modelMapper){
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<TicketDto> listeleme() {
        List<Ticket> ticket = ticketRepository.findAll();
        List<TicketDto> ticketDto = new ArrayList<>();
        for(Ticket t:ticket){
            ticketDto.add(modelMapper.map(t,TicketDto.class));
        }
        return ticketDto;
    }

    @Override
    public TicketDto kayitEkleme(TicketDto ticketDto) {
        Ticket ticketDb = modelMapper.map(ticketDto, Ticket.class);
        ticketDb = ticketRepository.save(ticketDb);
        return modelMapper.map(ticketDb, TicketDto.class);
    }

    @Override
    public TicketDto kayitBul(Long id) {
        Ticket ticket = ticketRepository.getOne(id);
        return modelMapper.map(ticket,TicketDto.class);
    }

    @Override
    public Boolean silme(Long id) {
        Ticket ticket = ticketRepository.getOne(id);
        if(ticket.getId().equals(null))
            throw new IllegalArgumentException("Bu id'li kayıt bulunamadı.");
        ticket.setCustomers(null);
        ticket.setCleander(null);
        ticketRepository.save(ticket);
        ticketRepository.deleteById(id);
        return true;
    }

    @Override
    public TicketDto guncelleme(Long id, TicketDto ticketDto) {
        Ticket ticket = ticketRepository.getOne(id);
        if(ticket.getId().equals(null))
            throw new IllegalArgumentException("Bu id'li kayıt bulunamadı.");

        ticket.setCleander(ticketDto.getCleanderDto());
        ticket.setCustomers(ticketDto.getCustomersDto());
        ticket.setPnr(ticketDto.getPnr());
        ticket.setPrice(ticketDto.getPrice());
        ticket.setSeatNumber(ticketDto.getSeatNumber());

        ticketRepository.save(ticket);
        return modelMapper.map(ticket,TicketDto.class);
    }
}
