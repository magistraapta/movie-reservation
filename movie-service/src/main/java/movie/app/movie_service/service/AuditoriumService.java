package movie.app.movie_service.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movie.app.movie_service.domain.dto.request.CreateAuditorium;
import movie.app.movie_service.domain.dto.response.AuditoriumResponse;
import movie.app.movie_service.domain.entity.Auditorium;
import movie.app.movie_service.domain.mapper.AuditoriumMapper;
import movie.app.movie_service.exception.AuditoriumNotFoundException;
import movie.app.movie_service.exception.DuplicateAuditoriumException;
import movie.app.movie_service.repository.AuditoriumRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuditoriumService {

    private final AuditoriumRepository auditoriumRepository;
    private final AuditoriumMapper auditoriumMapper;

    public List<AuditoriumResponse> getAllAuditoriums() {
        return auditoriumRepository.findAll().stream()
            .map(auditoriumMapper::auditoriumToAuditoriumDto)
            .collect(Collectors.toList());
    }

    public AuditoriumResponse getAuditoriumById(Long id) {
        return auditoriumMapper.auditoriumToAuditoriumDto(
            auditoriumRepository.findById(id)
                .orElseThrow(() -> new AuditoriumNotFoundException("Auditorium not found with id: " + id))
        );
    }

    @Transactional
    public AuditoriumResponse createAuditorium(CreateAuditorium createAuditorium) {
        // Check for duplicate name
        auditoriumRepository.findByNameIgnoreCase(createAuditorium.getName())
            .ifPresent(auditorium -> {
                throw new DuplicateAuditoriumException(
                    "Auditorium with name '" + createAuditorium.getName() + "' already exists"
                );
            });

        Auditorium auditorium = auditoriumMapper.auditoriumDtoToAuditorium(createAuditorium);
        Auditorium savedAuditorium = auditoriumRepository.save(auditorium);
        log.info("Created auditorium with id: {}", savedAuditorium.getId());
        return auditoriumMapper.auditoriumToAuditoriumDto(savedAuditorium);
    }

    @Transactional
    public void deleteAuditorium(Long id) {
        Auditorium auditorium = auditoriumRepository.findById(id)
            .orElseThrow(() -> new AuditoriumNotFoundException("Auditorium not found with id: " + id));
        
        // Check if auditorium has scheduled movies
        // This would require MovieScheduleRepository - we'll add this check in MovieScheduleService
        auditoriumRepository.delete(auditorium);
        log.info("Deleted auditorium with id: {}", id);
    }
}

