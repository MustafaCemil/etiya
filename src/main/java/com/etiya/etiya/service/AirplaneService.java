package com.etiya.etiya.service;

import com.etiya.etiya.dto.AirplaneDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirplaneService {

    Page<AirplaneDto> listeleme(Pageable pageable);

    AirplaneDto kayitEkleme(AirplaneDto airplaneDto);

    AirplaneDto kayitBul(Long id);

    Boolean silme(Long id);

    AirplaneDto guncelleme(Long id, AirplaneDto airplaneDto);
}
