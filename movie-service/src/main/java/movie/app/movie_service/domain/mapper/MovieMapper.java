package movie.app.movie_service.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import movie.app.movie_service.domain.dto.request.CreateMovieRequest;
import movie.app.movie_service.domain.dto.response.MovieResponse;
import movie.app.movie_service.domain.entity.Movie;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieResponse movieToMovieDto(Movie movie);

    @Mapping(target = "id", ignore = true)
    Movie movieDtoToMovie(CreateMovieRequest createMovieRequest);


}
