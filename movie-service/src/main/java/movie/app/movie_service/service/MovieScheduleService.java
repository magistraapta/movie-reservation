package movie.app.movie_service.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movie.app.movie_service.domain.dto.request.CreateMovieScheduleRequest;
import movie.app.movie_service.domain.dto.response.MovieScheduleResponse;
import movie.app.movie_service.domain.entity.Auditorium;
import movie.app.movie_service.domain.entity.Movie;
import movie.app.movie_service.domain.entity.MovieSchedule;
import movie.app.movie_service.domain.mapper.MovieScheduleMapper;
import movie.app.movie_service.exception.AuditoriumNotFoundException;
import movie.app.movie_service.exception.InvalidScheduleException;
import movie.app.movie_service.exception.MovieNotFoundException;
import movie.app.movie_service.exception.ScheduleConflictException;
import movie.app.movie_service.repository.AuditoriumRepository;
import movie.app.movie_service.repository.MovieRepository;
import movie.app.movie_service.repository.MovieScheduleRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieScheduleService {

    private final MovieScheduleRepository movieScheduleRepository;
    private final MovieRepository movieRepository;
    private final AuditoriumRepository auditoriumRepository;
    private final MovieScheduleMapper movieScheduleMapper;

    public List<MovieScheduleResponse> getAllSchedules() {
        return movieScheduleRepository.findAll().stream()
            .map(movieScheduleMapper::entityToResponse)
            .collect(Collectors.toList());
    }

    public MovieScheduleResponse getScheduleById(Long id) {
        MovieSchedule schedule = movieScheduleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Movie schedule not found with id: " + id));
        return movieScheduleMapper.entityToResponse(schedule);
    }

    public List<MovieScheduleResponse> getSchedulesByMovieId(Long movieId) {
        return movieScheduleRepository.findByMovieId(movieId).stream()
            .map(movieScheduleMapper::entityToResponse)
            .collect(Collectors.toList());
    }

    public List<MovieScheduleResponse> getSchedulesByAuditoriumId(Long auditoriumId) {
        return movieScheduleRepository.findByAuditoriumId(auditoriumId).stream()
            .map(movieScheduleMapper::entityToResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public MovieScheduleResponse createSchedule(CreateMovieScheduleRequest request) {
        // Validate movie exists
        Movie movie = movieRepository.findById(request.getMovieId())
            .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + request.getMovieId()));

        // Validate auditorium exists
        Auditorium auditorium = auditoriumRepository.findById(request.getAuditoriumId())
            .orElseThrow(() -> new AuditoriumNotFoundException("Auditorium not found with id: " + request.getAuditoriumId()));

        // Validate time constraints
        validateScheduleTimes(request.getStartTime(), request.getEndTime(), movie.getDuration());

        // Check for schedule conflicts
        List<MovieSchedule> conflictingSchedules = movieScheduleRepository
            .findOverlappingSchedulesForNew(
                request.getAuditoriumId(),
                request.getStartTime(),
                request.getEndTime()
            );

        if (!conflictingSchedules.isEmpty()) {
            throw new ScheduleConflictException(
                "Schedule conflicts with existing schedule(s) in auditorium. " +
                "Please choose a different time slot."
            );
        }

        // Create schedule
        MovieSchedule schedule = movieScheduleMapper.createRequestToEntity(request);
        schedule.setMovie(movie);
        schedule.setAuditorium(auditorium);

        MovieSchedule savedSchedule = movieScheduleRepository.save(schedule);
        log.info("Created movie schedule with id: {} for movie: {} in auditorium: {}", 
            savedSchedule.getId(), movie.getTitle(), auditorium.getName());
        
        return movieScheduleMapper.entityToResponse(savedSchedule);
    }

    @Transactional
    public void deleteSchedule(Long id) {
        MovieSchedule schedule = movieScheduleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Movie schedule not found with id: " + id));
        movieScheduleRepository.delete(schedule);
        log.info("Deleted movie schedule with id: {}", id);
    }

    /**
     * Validates that:
     * 1. End time is after start time
     * 2. Start time is not in the past
     * 3. Schedule duration is at least the movie duration (ensures movie can finish playing)
     */
    private void validateScheduleTimes(LocalDateTime startTime, LocalDateTime endTime, Integer movieDurationMinutes) {
        // Validate end time is after start time
        if (endTime.isBefore(startTime) || endTime.isEqual(startTime)) {
            throw new InvalidScheduleException("End time must be after start time");
        }

        // Validate start time is not in the past
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new InvalidScheduleException("Start time cannot be in the past");
        }

        // Calculate schedule duration
        Duration scheduleDuration = Duration.between(startTime, endTime);
        long scheduleMinutes = scheduleDuration.toMinutes();
        
        // Validate that schedule duration is at least the movie duration
        // This ensures the movie has enough time to finish playing
        if (scheduleMinutes < movieDurationMinutes) {
            throw new InvalidScheduleException(
                String.format("Schedule duration (%d minutes) is shorter than movie duration (%d minutes). " +
                    "The schedule must allow enough time for the movie to finish playing.", 
                    scheduleMinutes, movieDurationMinutes)
            );
        }
        
        // Note: We don't enforce a maximum duration as the schedule may include
        // cleaning time, previews, or other activities before/after the movie
    }
}

