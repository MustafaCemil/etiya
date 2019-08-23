package com.etiya.etiya.controller;

import com.etiya.etiya.dto.AirplaneDto;
import com.etiya.etiya.dto.CompanyDto;
import com.etiya.etiya.service.AirplaneService;
import com.etiya.etiya.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/ucak")
public class AirplaneController {

    @Autowired
    private AirplaneService airplaneService;

    public AirplaneController(AirplaneService airplaneService, CompanyService companyService){
        this.airplaneService = airplaneService;
    }

    @RequestMapping(value = "/listeleme",method = RequestMethod.GET)
    public ResponseEntity<List<AirplaneDto>> listeleme(Pageable pageable){
        try {
            return new ResponseEntity(airplaneService.listeleme(pageable), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET )
    public ResponseEntity<AirplaneDto> tekKayit(@PathVariable("id") Long id){
        AirplaneDto airplaneDto =  airplaneService.kayitBul(id);
        try {
            return new ResponseEntity(airplaneDto,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/ekle",method = RequestMethod.POST)
    public ResponseEntity<AirplaneDto> ekleme(@Valid @RequestBody AirplaneDto airplaneDto){
        try{
            return new ResponseEntity(airplaneService.kayitEkleme(airplaneDto),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/guncelle/{id}",method = RequestMethod.PUT)
    public ResponseEntity<AirplaneDto> guncelleme(@PathVariable("id") Long id, @Valid @RequestBody AirplaneDto airplaneDto){
        try {
            return new ResponseEntity(airplaneService.guncelleme(id,airplaneDto),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @RequestMapping(value = "/silme/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> silme(@PathVariable("id") Long id){
        try{
            return new ResponseEntity(airplaneService.silme(id),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
