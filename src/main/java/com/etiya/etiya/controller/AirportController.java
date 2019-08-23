package com.etiya.etiya.controller;

import com.etiya.etiya.dto.AirportDto;
import com.etiya.etiya.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/havalimani")
public class AirportController {

    @Autowired
    private AirportService airportService;

    public AirportController( AirportService airportService){
        this.airportService = airportService;
    }

    @RequestMapping(value = "/listeleme",method = RequestMethod.GET)
    public ResponseEntity<List<AirportDto>> listeleme(Pageable pageable){
        try {
            return new ResponseEntity(airportService.listeleme(pageable), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET )
    public ResponseEntity<AirportDto> tekKayit(@PathVariable("id") Long id){
        AirportDto airportDto =  airportService.kayitBul(id);
        try {
            return new ResponseEntity(airportDto,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/ekle",method = RequestMethod.POST)
    public ResponseEntity<AirportDto> ekleme(@Valid @RequestBody AirportDto airportDto){
        try{
            return new ResponseEntity(airportService.kayitEkleme(airportDto),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/guncelleme/{id}",method = RequestMethod.PUT)
    public ResponseEntity<AirportDto> guncelleme(@PathVariable("id") Long id, @Valid @RequestBody AirportDto airportDto){
        try {
            return new ResponseEntity(airportService.guncelleme(id,airportDto),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @RequestMapping(value = "/silme/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> silme(@PathVariable("id") Long id){
        try{
            return new ResponseEntity(airportService.silme(id),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
