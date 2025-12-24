package auth.app.auth_service.domain.dto;

import java.time.Instant;
import auth.app.auth_service.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;
    private String email;
    private UserRole role;
    private Instant createdAt;
    private Instant updatedAt;
}
