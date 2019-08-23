package com.etiya.etiya.controller;

import com.etiya.etiya.dto.TicketDto;
import com.etiya.etiya.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/bilet")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @RequestMapping(value = "/listeleme",method = RequestMethod.GET)
    public ResponseEntity<List<TicketDto>> listeleme(Pageable pageable){
        try {
            return new ResponseEntity(ticketService.listeleme(pageable), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET )
    public ResponseEntity<TicketDto> tekKayit(@PathVariable("id") Long id){
        TicketDto ticketDto =  ticketService.kayitBul(id);
        try {
            return new ResponseEntity(ticketDto,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/ekle",method = RequestMethod.POST)
    public ResponseEntity<TicketDto> ekleme(@Valid @RequestBody TicketDto ticketDto){
        try{
            return new ResponseEntity(ticketService.kayitEkleme(ticketDto),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/guncelleme/{id}",method = RequestMethod.PUT)
    public ResponseEntity<TicketDto> guncelleme(@PathVariable("id") Long id, @Valid @RequestBody TicketDto ticketDto){
        try {
            return new ResponseEntity(ticketService.guncelleme(id,ticketDto),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @RequestMapping(value = "/silme/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> silme(@PathVariable("id") Long id){
        try{
            return new ResponseEntity(ticketService.silme(id),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
