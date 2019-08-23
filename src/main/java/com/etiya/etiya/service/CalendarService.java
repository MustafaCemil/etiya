package com.etiya.etiya.service;

import com.etiya.etiya.dto.CalendarDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CalendarService {

    Page<CalendarDto> listeleme(Pageable pageable);

    CalendarDto kayitEkleme(CalendarDto calendarDto);

    CalendarDto kayitBul(Long id);

    Boolean silme(Long id);

    CalendarDto guncelleme(Long id, CalendarDto calendarDto);
}
