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
import movie.app.movie_service.domain.dto.response.MovieResponse;
import movie.app.movie_service.service.MovieService;


@RestController
@RequestMapping("/api/movie")
@RequiredArgsConstructor
public class MovieController {
    
    private final MovieService movieService;

    @GetMapping("/health")
    public ResponseEntity<String> checkServerStatus() {
        return ResponseEntity.status(HttpStatus.OK).body("Movie Service is running");
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getMovieById(id));
    }

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@RequestBody CreateMovieRequest createMovieRequest) {
        MovieResponse movieResponse = movieService.createMovie(createMovieRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted movie");
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable Long id, @RequestBody MovieResponse movieResponse) {
        return ResponseEntity.status(HttpStatus.OK).body(movieService.updateMovie(id, movieResponse));
    }

}
