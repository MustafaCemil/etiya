package com.etiya.etiya.service.impl;

import com.etiya.etiya.dto.CleanderDto;
import com.etiya.etiya.dto.CustomersDto;
import com.etiya.etiya.entity.Cleander;
import com.etiya.etiya.entity.Customers;
import com.etiya.etiya.repository.CleanderRepository;
import com.etiya.etiya.repository.CustomersRepository;
import com.etiya.etiya.util.TPage;
import com.etiya.etiya.dto.TicketDto;
import com.etiya.etiya.entity.Ticket;
import com.etiya.etiya.repository.TicketRepository;
import com.etiya.etiya.service.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CleanderRepository cleanderRepository;
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private ModelMapper modelMapper;

    public TicketServiceImpl( CleanderRepository cleanderRepository,TicketRepository ticketRepository,
                              CustomersRepository customersRepository, ModelMapper modelMapper){
        this.ticketRepository = ticketRepository;
        this.cleanderRepository = cleanderRepository;
        this.customersRepository = customersRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<TicketDto> listeleme(Pageable pageable) {
        Page<Ticket> data = ticketRepository.findAll(pageable);
        TPage tpage = new TPage<TicketDto>();
        TicketDto[] dtos = modelMapper.map(data.getContent(),TicketDto[].class);
        tpage.setStat(data, Arrays.asList(dtos));
        return (Page<TicketDto>) tpage;
    }

    @Override
    public TicketDto kayitEkleme(TicketDto ticketDto) {
        CleanderDto cleanderDto = ticketDto.getCleanderDto();
        CustomersDto customersDto = ticketDto.getCustomersDto();
        Long customersId = customersDto.getId();
        Long cleanderId = cleanderDto.getId();
        Customers customers = customersRepository.getOne(customersId);
        Cleander cleander = cleanderRepository.getOne(cleanderId);
        Ticket ticketDb = modelMapper.map(ticketDto, Ticket.class);

        //PNR random uretme
        Random random=new Random();
        String firstName = customers.getFirstName();
        String lastName = customers.getLastName();
        String phoneNumber = customers.getPhoneNumber();

        int randFn = random.nextInt(firstName.length());
        int randLn = random.nextInt(lastName.length());
        int randPn = random.nextInt(phoneNumber.length());
        char fn = firstName.charAt(randFn);
        char ln = lastName.charAt(randLn);
        char pn = phoneNumber.charAt(randPn);

        String pnr = "PNR" + fn + ln + pn;

        //koltuk numarasi sirayla atama
        Integer seatNumber = cleander.getSeatNumber();
        Integer seatFull = cleander.getSeatFull();
        Integer ticketSeat = 0;
        Integer seatUpdate = 0;

        if(seatNumber.equals(0)) {
            System.err.println("Ucak dolu");
        } else {
            ticketSeat = (seatFull + 1) - seatNumber;
        }
        seatUpdate = seatFull - ticketSeat;
        cleander.setSeatNumber(seatUpdate);
        cleanderRepository.save(cleander);

        //ucretlendirme
        Float priceTicket = cleander.getPrice();
        String discountCoupon = customers.getDiscountCoupon();

        if(discountCoupon.equals(null)) {
            ticketDb.setPrice(priceTicket);

        } else{
            Float newPrice = priceTicket - (priceTicket / 10);
            ticketDb.setPrice(newPrice);
        }

        ticketDb.setSeatNumber(ticketSeat);
        ticketDb.setPnr(pnr);
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

        CleanderDto cleanderDto = ticketDto.getCleanderDto();
        Cleander cleander = modelMapper.map(cleanderDto, Cleander.class);

        CustomersDto customersDto = ticketDto.getCustomersDto();
        Customers customers = modelMapper.map(customersDto,Customers.class);

        if(ticket.getId().equals(null))
            throw new IllegalArgumentException("Bu id'li kayıt bulunamadı.");

        ticket.setCleander(cleander);
        ticket.setCustomers(customers);
        ticket.setPnr(ticketDto.getPnr());
        ticket.setPrice(ticketDto.getPrice());
        ticket.setSeatNumber(ticketDto.getSeatNumber());

        ticketRepository.save(ticket);
        return modelMapper.map(ticket,TicketDto.class);
    }
}
