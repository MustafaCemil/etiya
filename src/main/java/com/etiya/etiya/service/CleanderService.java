package com.etiya.etiya.service;

import com.etiya.etiya.dto.CleanderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CleanderService {

    Page<CleanderDto> listeleme(Pageable pageable);

    CleanderDto kayitEkleme(CleanderDto cleanderDto);

    CleanderDto kayitBul(Long id);

    Boolean silme(Long id);

    CleanderDto guncelleme(Long id, CleanderDto cleanderDto);
}
