package com.etiya.etiya.controller;

import com.etiya.etiya.dto.CompanyDto;
import com.etiya.etiya.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/sirket")
@Api(value = "Project APIs")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @RequestMapping(value = "/listeleme",method = RequestMethod.GET)
    @ApiOperation(value = "Listeleme işlemi", response = CompanyDto.class)
    public ResponseEntity<List<CompanyDto>> listeleme(){
        try {
            return new ResponseEntity(companyService.listeleme(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET )
    @ApiOperation(value = "Tek kayit bulma", response = CompanyDto.class)
    public ResponseEntity<CompanyDto> tekKayit(@PathVariable("id") Long id){
        CompanyDto companyDto =  companyService.kayitBul(id);
        try {
            return new ResponseEntity(companyDto,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/ekle",method = RequestMethod.POST)
    @ApiOperation(value = "Ekleme işlemi", response = CompanyDto.class)
    public ResponseEntity<CompanyDto> ekleme(@Valid @RequestBody CompanyDto companyDto){
        try{
            return new ResponseEntity(companyService.kayitEkleme(companyDto),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ApiOperation(value = "Güncelleme işlemi", response = CompanyDto.class)
    public ResponseEntity<CompanyDto> guncelleme(@PathVariable("id") Long id, @Valid @RequestBody CompanyDto companyDto){
        try {
            return new ResponseEntity(companyService.guncelleme(id,companyDto),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Silme işlemi", response = CompanyDto.class)
    public ResponseEntity<Boolean> silme(@PathVariable("id") Long id){
        try{
            return new ResponseEntity(companyService.silme(id),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
