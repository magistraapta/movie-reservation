package tiket.service.tiket_service.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tiket.service.tiket_service.domain.dto.ticket.CreateTicketRequest;
import tiket.service.tiket_service.domain.dto.ticket.TicketResponse;
import tiket.service.tiket_service.domain.entity.EventEntity;
import tiket.service.tiket_service.domain.entity.TicketEntity;
import tiket.service.tiket_service.domain.enums.TicketStatus;
import tiket.service.tiket_service.domain.mapper.TicketMapper;
import tiket.service.tiket_service.exception.NotFoundException;
import tiket.service.tiket_service.repository.EventRepository;
import tiket.service.tiket_service.repository.TicketRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {
    
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final TicketMapper ticketMapper;

    public TicketResponse reserveTicket(CreateTicketRequest request) {
        log.info("Reserving ticket: {}", request);
        
        // Fetch the event entity
        EventEntity event = eventRepository.findById(request.getEventId())
            .orElseThrow(() -> new NotFoundException("Event with id " + request.getEventId() + " not found"));
        
        TicketEntity ticket = ticketMapper.toEntity(request);
        ticket.setEvent(event);
        ticket.setStatus(TicketStatus.RESERVED);
        ticket.setReservedAt(Instant.now());
        ticket.setExpiresAt(Instant.now().plus(5, ChronoUnit.MINUTES));

        TicketEntity savedTicket = ticketRepository.save(ticket);
        return ticketMapper.toResponse(savedTicket);
    }

    public TicketResponse getTicketById(Long id) {
        log.info("Getting ticket by id: {}", id);
        TicketEntity ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Ticket with id " + id + " not found"));
        return ticketMapper.toResponse(ticket);
    }
}
