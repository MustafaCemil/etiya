package com.etiya.etiya.controller;

import com.etiya.etiya.dto.CleanderDto;
import com.etiya.etiya.service.AirplaneService;
import com.etiya.etiya.service.CleanderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/takvim")
@Api(value = "Project APIs")
public class CleanderController {

    @Autowired
    private CleanderService cleanderService;

    public CleanderController(CleanderService cleanderService){
        this.cleanderService = cleanderService;
    }

    @RequestMapping(value = "/listeleme",method = RequestMethod.GET)
    @ApiOperation(value = "Listeleme işlemi", response = CleanderDto.class)
    public ResponseEntity<List<CleanderDto>> listeleme(Pageable pageable){
        try {
            return new ResponseEntity(cleanderService.listeleme(pageable), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET )
    @ApiOperation(value = "Tek kayit bulma", response = CleanderDto.class)
    public ResponseEntity<CleanderDto> tekKayit(@PathVariable("id") Long id){
        CleanderDto cleanderDto =  cleanderService.kayitBul(id);
        try {
            return new ResponseEntity(cleanderDto,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/ekle",method = RequestMethod.POST)
    @ApiOperation(value = "Ekleme işlemi", response = CleanderDto.class)
    public ResponseEntity<CleanderDto> ekleme(@Valid @RequestBody CleanderDto cleanderDto){
        try{
            return new ResponseEntity(cleanderService.kayitEkleme(cleanderDto),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ApiOperation(value = "Güncelleme işlemi", response = CleanderDto.class)
    public ResponseEntity<CleanderDto> guncelleme(@PathVariable("id") Long id, @Valid @RequestBody CleanderDto cleanderDto){
        try {
            return new ResponseEntity(cleanderService.guncelleme(id,cleanderDto),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Silme işlemi", response = CleanderDto.class)
    public ResponseEntity<Boolean> silme(@PathVariable("id") Long id){
        try{
            return new ResponseEntity(cleanderService.silme(id),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
