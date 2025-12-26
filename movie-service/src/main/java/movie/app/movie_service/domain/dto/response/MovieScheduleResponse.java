package movie.app.movie_service.domain.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieScheduleResponse {
    private Long id;
    private MovieResponse movie;
    private AuditoriumResponse auditorium;
    private BigDecimal price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

