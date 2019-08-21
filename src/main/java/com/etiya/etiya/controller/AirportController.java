package com.etiya.etiya.controller;

import com.etiya.etiya.dto.AirportDto;
import com.etiya.etiya.service.AirportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/havalimani")
@Api(value = "Project APIs")
public class AirportController {

    @Autowired
    private AirportService airportService;

    public AirportController( AirportService airportService){
        this.airportService = airportService;
    }

    @RequestMapping(value = "/listeleme",method = RequestMethod.GET)
    @ApiOperation(value = "Listeleme işlemi", response = AirportDto.class)
    public ResponseEntity<List<AirportDto>> listeleme(){
        try {
            return new ResponseEntity(airportService.listeleme(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET )
    @ApiOperation(value = "Tek kayit bulma", response = AirportDto.class)
    public ResponseEntity<AirportDto> tekKayit(@PathVariable("id") Long id){
        AirportDto airportDto =  airportService.kayitBul(id);
        try {
            return new ResponseEntity(airportDto,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/ekle",method = RequestMethod.POST)
    @ApiOperation(value = "Ekleme işlemi", response = AirportDto.class)
    public ResponseEntity<AirportDto> ekleme(@Valid @RequestBody AirportDto airportDto){
        try{
            return new ResponseEntity(airportService.kayitEkleme(airportDto),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ApiOperation(value = "Güncelleme işlemi", response = AirportDto.class)
    public ResponseEntity<AirportDto> guncelleme(@PathVariable("id") Long id, @Valid @RequestBody AirportDto airportDto){
        try {
            return new ResponseEntity(airportService.guncelleme(id,airportDto),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Silme işlemi", response = AirportDto.class)
    public ResponseEntity<Boolean> silme(@PathVariable("id") Long id){
        try{
            return new ResponseEntity(airportService.silme(id),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
