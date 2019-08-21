package com.etiya.etiya.service;

import com.etiya.etiya.dto.CleanderDto;

import java.util.List;

public interface CleanderService {

    public List<CleanderDto> listeleme();

    CleanderDto kayitEkleme(CleanderDto cleanderDto);

    CleanderDto kayitBul(Long id);

    Boolean silme(Long id);

    CleanderDto guncelleme(Long id, CleanderDto cleanderDto);
}
