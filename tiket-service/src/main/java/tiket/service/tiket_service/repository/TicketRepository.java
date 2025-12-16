package tiket.service.tiket_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tiket.service.tiket_service.domain.entity.TicketEntity;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    
}
