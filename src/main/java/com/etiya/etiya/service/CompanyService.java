package com.etiya.etiya.service;

import com.etiya.etiya.dto.CompanyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {

    Page<CompanyDto> listeleme(Pageable pageable);

    CompanyDto kayitEkleme(CompanyDto companyDto);

    CompanyDto kayitBul(Long id);

    Boolean silme(Long id);

    CompanyDto guncelleme(Long id, CompanyDto companyDto);
}
