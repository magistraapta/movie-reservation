package movie.app.movie_service.domain.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private LocalDate releaseDate;
}
