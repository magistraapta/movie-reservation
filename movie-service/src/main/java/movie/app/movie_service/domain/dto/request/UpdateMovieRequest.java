package movie.app.movie_service.domain.dto.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMovieRequest {
    private String title;
    private String description;
    private Integer duration;
    private LocalDate releaseDate;
}
