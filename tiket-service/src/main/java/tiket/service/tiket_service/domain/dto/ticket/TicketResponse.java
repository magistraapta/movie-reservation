package tiket.service.tiket_service.domain.dto.ticket;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private Long id;
    private Long eventId;
    private Long userId;
    private String status;
    private Instant reservedAt;
    private Instant expiresAt;
}
