package tiket.service.tiket_service.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tiket.service.tiket_service.domain.dto.ticket.CreateTicketRequest;
import tiket.service.tiket_service.domain.dto.ticket.TicketResponse;
import tiket.service.tiket_service.domain.entity.TicketEntity;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketEntity toEntity(CreateTicketRequest request);
    
    @Mapping(source = "event.id", target = "eventId")
    TicketResponse toResponse(TicketEntity entity);
}
