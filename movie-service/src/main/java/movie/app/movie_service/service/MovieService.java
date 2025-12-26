package movie.app.movie_service.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movie.app.movie_service.domain.dto.request.CreateMovieRequest;
import movie.app.movie_service.domain.dto.request.UpdateMovieRequest;
import movie.app.movie_service.domain.dto.response.MovieResponse;
import movie.app.movie_service.domain.entity.Movie;
import movie.app.movie_service.domain.mapper.MovieMapper;
import movie.app.movie_service.exception.MovieNotFoundException;
import movie.app.movie_service.repository.MovieRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll().stream()
            .map(movieMapper::movieToMovieDto)
            .collect(Collectors.toList());
    }

    public MovieResponse getMovieById(Long id) {
        return movieMapper.movieToMovieDto(movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie not found")));
    }

    @Transactional
    public MovieResponse createMovie(CreateMovieRequest createMovieRequest) {
        Movie movie = movieMapper.movieDtoToMovie(createMovieRequest);
        Movie savedMovie = movieRepository.save(movie);
        return movieMapper.movieToMovieDto(savedMovie);
    }

    @Transactional
    public MovieResponse updateMovie(Long id, UpdateMovieRequest updateMovieRequest) {

        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        if (updateMovieRequest.getTitle() != null) {
            movie.setTitle(updateMovieRequest.getTitle());
        }
        if (updateMovieRequest.getDescription() != null) {
            movie.setDescription(updateMovieRequest.getDescription());
        }
        if (updateMovieRequest.getDuration() != null) {
            movie.setDuration(updateMovieRequest.getDuration());
        }
        if (updateMovieRequest.getReleaseDate() != null) {
            movie.setReleaseDate(updateMovieRequest.getReleaseDate());
        }

        movieRepository.save(movie);
        return movieMapper.movieToMovieDto(movie);
    }

    @Transactional
    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        movieRepository.delete(movie);
    }
}
