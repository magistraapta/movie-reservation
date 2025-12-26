package movie.app.movie_service.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import movie.app.movie_service.domain.entity.Auditorium;

public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {
    
    Optional<Auditorium> findByNameIgnoreCase(String name);
}
