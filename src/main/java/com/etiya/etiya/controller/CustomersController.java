package com.etiya.etiya.controller;

import com.etiya.etiya.dto.CustomersDto;
import com.etiya.etiya.service.CustomersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/musteri")
@Api(value = "Project APIs")
public class CustomersController {

    @Autowired
    private CustomersService customersService;

    public CustomersController(CustomersService customersService){
        this.customersService = customersService;
    }

    @RequestMapping(value = "/listeleme",method = RequestMethod.GET)
    @ApiOperation(value = "Listeleme işlemi", response = CustomersDto.class)
    public ResponseEntity<List<CustomersDto>> listeleme(){
        try {
            return new ResponseEntity(customersService.listeleme(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET )
    @ApiOperation(value = "Tek kayit bulma", response = CustomersDto.class)
    public ResponseEntity<CustomersDto> tekKayit(@PathVariable("id") Long id){
        CustomersDto customersDto =  customersService.kayitBul(id);
        try {
            return new ResponseEntity(customersDto,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/ekle",method = RequestMethod.POST)
    @ApiOperation(value = "Ekleme işlemi", response = CustomersDto.class)
    public ResponseEntity<CustomersDto> ekleme(@Valid @RequestBody CustomersDto customersDto){
        try{
            return new ResponseEntity(customersService.kayitEkleme(customersDto),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ApiOperation(value = "Güncelleme işlemi", response = CustomersDto.class)
    public ResponseEntity<CustomersDto> guncelleme(@PathVariable("id") Long id, @Valid @RequestBody CustomersDto customersDto){
        try {
            return new ResponseEntity(customersService.guncelleme(id,customersDto),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Silme işlemi", response = CustomersDto.class)
    public ResponseEntity<Boolean> silme(@PathVariable("id") Long id){
        try{
            return new ResponseEntity(customersService.silme(id),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
