package com.etiya.etiya.service.impl;

import com.etiya.etiya.dto.CustomersDto;
import com.etiya.etiya.entity.Customers;
import com.etiya.etiya.repository.CustomersRepository;
import com.etiya.etiya.service.CustomersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomersService {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CustomerServiceImpl(CustomersRepository customersRepository,ModelMapper modelMapper){
        this.customersRepository = customersRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CustomersDto> listeleme() {
        List<Customers> customers = customersRepository.findAll();
        List<CustomersDto> customersDto = new ArrayList<>();
        for(Customers c:customers){
            customersDto.add(modelMapper.map(c,CustomersDto.class));
        }
        return customersDto;
    }

    @Override
    public CustomersDto kayitEkleme(CustomersDto customersDto) {
        Customers customersDb = modelMapper.map(customersDto, Customers.class);
        customersDb = customersRepository.save(customersDb);
        return modelMapper.map(customersDb, CustomersDto.class);
    }

    @Override
    public CustomersDto kayitBul(Long id) {
        Customers customers = customersRepository.getOne(id);
        return modelMapper.map(customers,CustomersDto.class);
    }

    @Override
    public Boolean silme(Long id) {
        Customers customers = customersRepository.getOne(id);
        if(customers.getId().equals(null))
            throw new IllegalArgumentException("Bu id'li kayıt bulunamadı.");
        customersRepository.deleteById(id);
        return true;
    }

    @Override
    public CustomersDto guncelleme(Long id, CustomersDto customersDto) {
        Customers customers = customersRepository.getOne(id);
        if(customers.getId().equals(null))
            throw new IllegalArgumentException("Bu id'li kayıt bulunamadı.");

        customers.setFirstName(customersDto.getFirstName());
        customers.setLastName(customersDto.getLastName());
        customers.setEmail(customersDto.getEmail());
        customers.setGender(customersDto.getGender());
        customers.setBirthDay(customersDto.getBirthDay());
        customers.setPhoneNumber(customersDto.getPhoneNumber());

        customersRepository.save(customers);
        return modelMapper.map(customers,CustomersDto.class);
    }
}
