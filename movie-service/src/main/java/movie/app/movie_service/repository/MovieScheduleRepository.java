package movie.app.movie_service.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import movie.app.movie_service.domain.entity.MovieSchedule;

public interface MovieScheduleRepository extends JpaRepository<MovieSchedule, Long> {
    
    /**
     * Find schedules that overlap with the given time range in the same auditorium.
     * Two schedules overlap if:
     * - New start time is between existing start and end time, OR
     * - New end time is between existing start and end time, OR
     * - New schedule completely contains an existing schedule
     */
    @Query("SELECT ms FROM MovieSchedule ms WHERE ms.auditorium.id = :auditoriumId " +
           "AND ms.id != :excludeId " +
           "AND ((ms.startTime <= :startTime AND ms.endTime > :startTime) OR " +
           "     (ms.startTime < :endTime AND ms.endTime >= :endTime) OR " +
           "     (ms.startTime >= :startTime AND ms.endTime <= :endTime))")
    List<MovieSchedule> findOverlappingSchedules(
        @Param("auditoriumId") Long auditoriumId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime,
        @Param("excludeId") Long excludeId
    );
    
    /**
     * Find overlapping schedules for creating new schedule (no excludeId needed)
     */
    @Query("SELECT ms FROM MovieSchedule ms WHERE ms.auditorium.id = :auditoriumId " +
           "AND ((ms.startTime <= :startTime AND ms.endTime > :startTime) OR " +
           "     (ms.startTime < :endTime AND ms.endTime >= :endTime) OR " +
           "     (ms.startTime >= :startTime AND ms.endTime <= :endTime))")
    List<MovieSchedule> findOverlappingSchedulesForNew(
        @Param("auditoriumId") Long auditoriumId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );
    
    List<MovieSchedule> findByMovieId(Long movieId);
    
    List<MovieSchedule> findByAuditoriumId(Long auditoriumId);
}

