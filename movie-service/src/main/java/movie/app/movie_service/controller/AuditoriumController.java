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
import movie.app.movie_service.domain.dto.request.CreateAuditorium;
import movie.app.movie_service.domain.dto.response.AuditoriumResponse;
import movie.app.movie_service.service.AuditoriumService;
import movie.app.movie_service.shared.ApiResponse;

@RestController
@RequestMapping("/api/auditorium")
@RequiredArgsConstructor
public class AuditoriumController {
    
    private final AuditoriumService auditoriumService;

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> checkServerStatus() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success("Auditorium Service is running", "Server is running"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuditoriumResponse>>> getAllAuditoriums() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(
                auditoriumService.getAllAuditoriums(), 
                "Auditoriums fetched successfully"
            ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuditoriumResponse>> getAuditoriumById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(
                auditoriumService.getAuditoriumById(id), 
                "Auditorium fetched successfully"
            ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AuditoriumResponse>> createAuditorium(
            @Valid @RequestBody CreateAuditorium createAuditorium) {
        AuditoriumResponse auditoriumResponse = auditoriumService.createAuditorium(createAuditorium);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(auditoriumResponse, "Auditorium created successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteAuditorium(@PathVariable Long id) {
        auditoriumService.deleteAuditorium(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success("Successfully deleted auditorium", "Auditorium deleted successfully"));
    }
}

