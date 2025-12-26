package movie.app.movie_service.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import movie.app.movie_service.domain.dto.request.CreateAuditorium;
import movie.app.movie_service.domain.dto.response.AuditoriumResponse;
import movie.app.movie_service.domain.entity.Auditorium;

@Mapper(componentModel = "spring")
public interface AuditoriumMapper {
    
    AuditoriumResponse auditoriumToAuditoriumDto(Auditorium auditorium);

    @Mapping(target = "id", ignore = true)
    Auditorium auditoriumDtoToAuditorium(CreateAuditorium createAuditorium);

}
