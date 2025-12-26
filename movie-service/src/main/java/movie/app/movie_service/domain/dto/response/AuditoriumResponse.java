package movie.app.movie_service.domain.dto.response;

import lombok.Data;

@Data
public class AuditoriumResponse {
    private Long id;
    private String name;
    private Integer capacity;
}
