package com.etiya.etiya.service;

import com.etiya.etiya.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    List<CompanyDto> listeleme();

    CompanyDto kayitEkleme(CompanyDto companyDto);

    CompanyDto kayitBul(Long id);

    Boolean silme(Long id);

    CompanyDto guncelleme(Long id, CompanyDto companyDto);
}
