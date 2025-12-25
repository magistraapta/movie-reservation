package movie.app.movie_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import movie.app.movie_service.domain.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
