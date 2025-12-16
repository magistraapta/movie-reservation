package tiket.service.tiket_service.domain.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketRequest {
    private Long eventId;
    private Long userId;    
}
