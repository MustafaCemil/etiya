package com.etiya.etiya.service;

import com.etiya.etiya.dto.TicketDto;

import java.util.List;

public interface TicketService {

    List<TicketDto> listeleme();

    TicketDto kayitEkleme(TicketDto ticketDto);

    TicketDto kayitBul(Long id);

    Boolean silme(Long id);

    TicketDto guncelleme(Long id, TicketDto ticketDto);
}
