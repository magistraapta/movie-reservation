package movie.app.movie_service.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import movie.app.movie_service.domain.dto.request.CreateMovieScheduleRequest;
import movie.app.movie_service.domain.dto.response.MovieScheduleResponse;
import movie.app.movie_service.service.MovieScheduleService;
import movie.app.movie_service.shared.ApiResponse;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class MovieScheduleController {
    
    private final MovieScheduleService movieScheduleService;

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> checkServerStatus() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success("Movie Schedule Service is running", "Server is running"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovieScheduleResponse>>> getAllSchedules() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(
                movieScheduleService.getAllSchedules(), 
                "Schedules fetched successfully"
            ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieScheduleResponse>> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(
                movieScheduleService.getScheduleById(id), 
                "Schedule fetched successfully"
            ));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse<List<MovieScheduleResponse>>> getSchedulesByMovieId(
            @PathVariable Long movieId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(
                movieScheduleService.getSchedulesByMovieId(movieId), 
                "Schedules fetched successfully"
            ));
    }

    @GetMapping("/auditorium/{auditoriumId}")
    public ResponseEntity<ApiResponse<List<MovieScheduleResponse>>> getSchedulesByAuditoriumId(
            @PathVariable Long auditoriumId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(
                movieScheduleService.getSchedulesByAuditoriumId(auditoriumId), 
                "Schedules fetched successfully"
            ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MovieScheduleResponse>> createSchedule(
            @Valid @RequestBody CreateMovieScheduleRequest createScheduleRequest) {
        MovieScheduleResponse scheduleResponse = movieScheduleService.createSchedule(createScheduleRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(scheduleResponse, "Movie schedule created successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSchedule(@PathVariable Long id) {
        movieScheduleService.deleteSchedule(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success("Successfully deleted schedule", "Schedule deleted successfully"));
    }
}

