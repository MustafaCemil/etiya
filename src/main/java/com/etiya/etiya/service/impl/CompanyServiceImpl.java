package com.etiya.etiya.service.impl;

import com.etiya.etiya.util.TPage;
import com.etiya.etiya.dto.CompanyDto;
import com.etiya.etiya.entity.Company;
import com.etiya.etiya.repository.CompanyRepository;
import com.etiya.etiya.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper){
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<CompanyDto> listeleme(Pageable pageable) {
        Page<Company> data = companyRepository.findAll(pageable);
        TPage tpage = new TPage<CompanyDto>();
        CompanyDto[] dtos = modelMapper.map(data.getContent(),CompanyDto[].class);
        tpage.setStat(data, Arrays.asList(dtos));
        return (Page<CompanyDto>) tpage;
    }

    @Override
    public CompanyDto kayitEkleme(CompanyDto companyDto) {
        Company companyDb = modelMapper.map(companyDto, Company.class);
        companyDb = companyRepository.save(companyDb);
        return modelMapper.map(companyDb, CompanyDto.class);
    }

    @Override
    public CompanyDto kayitBul(Long id) {
        Company company = companyRepository.getOne(id);
        return modelMapper.map(company,CompanyDto.class);
    }

    @Override
    public Boolean silme(Long id) {
        companyRepository.deleteById(id);
        return true;
    }

    @Override
    public CompanyDto guncelleme(Long id, CompanyDto companyDto) {
        Company company = companyRepository.getOne(id);
        if(company.getId().equals(null))
            throw new IllegalArgumentException("Bu id'li kayıt bulunamadı.");
        company.setCompanyName(companyDto.getCompanyName());
        companyRepository.save(company);
        return modelMapper.map(company,CompanyDto.class);
    }
}
