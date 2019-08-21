package com.etiya.etiya.controller;

import com.etiya.etiya.dto.AirplaneDto;
import com.etiya.etiya.service.AirplaneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/ucak")
@Api(value = "Project APIs")
public class AirplaneController {

    @Autowired
    private AirplaneService airplaneService;

    public AirplaneController(AirplaneService airplaneService){
        this.airplaneService = airplaneService;
    }

    @RequestMapping(value = "/listeleme",method = RequestMethod.GET)
    @ApiOperation(value = "Listeleme işlemi", response = AirplaneDto.class)
    public ResponseEntity<List<AirplaneDto>> listeleme(){
        try {
            return new ResponseEntity(airplaneService.listeleme(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET )
    @ApiOperation(value = "Tek kayit bulma", response = AirplaneDto.class)
    public ResponseEntity<AirplaneDto> tekKayit(@PathVariable("id") Long id){
        AirplaneDto airplaneDto =  airplaneService.kayitBul(id);
        try {
            return new ResponseEntity(airplaneDto,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/ekle",method = RequestMethod.POST)
    @ApiOperation(value = "Ekleme işlemi", response = AirplaneDto.class)
    public ResponseEntity<AirplaneDto> ekleme(@Valid @RequestBody AirplaneDto airplaneDto){
        try{
            return new ResponseEntity(airplaneService.kayitEkleme(airplaneDto),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ApiOperation(value = "Güncelleme işlemi", response = AirplaneDto.class)
    public ResponseEntity<AirplaneDto> guncelleme(@PathVariable("id") Long id, @Valid @RequestBody AirplaneDto airplaneDto){
        try {
            return new ResponseEntity(airplaneService.guncelleme(id,airplaneDto),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Silme işlemi", response = AirplaneDto.class)
    public ResponseEntity<Boolean> silme(@PathVariable("id") Long id){
        try{
            return new ResponseEntity(airplaneService.silme(id),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
