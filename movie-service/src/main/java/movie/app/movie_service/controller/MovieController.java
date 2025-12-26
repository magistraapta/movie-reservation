package movie.app.movie_service.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import movie.app.movie_service.domain.dto.request.CreateMovieRequest;
import movie.app.movie_service.domain.dto.request.UpdateMovieRequest;
import movie.app.movie_service.domain.dto.response.MovieResponse;
import movie.app.movie_service.service.MovieService;
import movie.app.movie_service.shared.ApiResponse;


@RestController
@RequestMapping("/api/movie")
@RequiredArgsConstructor
public class MovieController {
    
    private final MovieService movieService;

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> checkServerStatus() {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Movie Service is running", "Server is running"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovieResponse>>> getAllMovies() {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(movieService.getAllMovies(), "Movies fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponse>> getMovieById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(movieService.getMovieById(id), "Movie fetched successfully"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MovieResponse>> createMovie(@RequestBody CreateMovieRequest createMovieRequest) {
        MovieResponse movieResponse = movieService.createMovie(createMovieRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(movieResponse, "Movie created successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Successfully deleted movie", "Movie deleted successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponse>> updateMovie(@PathVariable Long id, @RequestBody UpdateMovieRequest updateMovieRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(movieService.updateMovie(id, updateMovieRequest), "Movie updated successfully"));
    }

}
