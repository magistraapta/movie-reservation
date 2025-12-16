package tiket.service.tiket_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import tiket.service.tiket_service.domain.dto.ticket.CreateTicketRequest;
import tiket.service.tiket_service.domain.dto.ticket.TicketResponse;
import tiket.service.tiket_service.service.TicketService;
import tiket.service.tiket_service.shared.ApiResponse;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/reserve")
    public ResponseEntity<ApiResponse<TicketResponse>> reserveTicket(@RequestBody CreateTicketRequest request) {
        return ResponseEntity.ok(ApiResponse.success(ticketService.reserveTicket(request), "Ticket reserved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TicketResponse>> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(ticketService.getTicketById(id), "Ticket found successfully"));
    }
}
