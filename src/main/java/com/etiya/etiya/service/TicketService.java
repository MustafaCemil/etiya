package com.etiya.etiya.service;

import com.etiya.etiya.dto.TicketDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketService {

    Page<TicketDto> listeleme(Pageable pageable);

    TicketDto kayitEkleme(TicketDto ticketDto);

    TicketDto kayitBul(Long id);

    Boolean silme(Long id);

    TicketDto guncelleme(Long id, TicketDto ticketDto);
}
