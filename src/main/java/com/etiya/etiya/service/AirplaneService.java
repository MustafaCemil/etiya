package com.etiya.etiya.service;

import com.etiya.etiya.dto.AirplaneDto;

import java.util.List;

public interface AirplaneService {

    List<AirplaneDto> listeleme();

    AirplaneDto kayitEkleme(AirplaneDto airplaneDto);

    AirplaneDto kayitBul(Long id);

    Boolean silme(Long id);

    AirplaneDto guncelleme(Long id, AirplaneDto airplaneDto);
}
