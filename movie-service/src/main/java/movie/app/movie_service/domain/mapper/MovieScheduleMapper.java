package movie.app.movie_service.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import movie.app.movie_service.domain.dto.request.CreateMovieScheduleRequest;
import movie.app.movie_service.domain.dto.response.MovieScheduleResponse;
import movie.app.movie_service.domain.entity.MovieSchedule;

@Mapper(componentModel = "spring", uses = {MovieMapper.class, AuditoriumMapper.class})
public interface MovieScheduleMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "auditorium", ignore = true)
    MovieSchedule createRequestToEntity(CreateMovieScheduleRequest request);
    
    @Mapping(source = "movie", target = "movie")
    @Mapping(source = "auditorium", target = "auditorium")
    MovieScheduleResponse entityToResponse(MovieSchedule movieSchedule);
}

