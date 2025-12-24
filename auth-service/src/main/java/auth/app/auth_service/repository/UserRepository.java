package auth.app.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import auth.app.auth_service.domain.dto.UserAuthDto;
import auth.app.auth_service.domain.entity.User;
import auth.app.auth_service.exception.UsernameNotFoundException;

public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT new auth.app.auth_service.domain.dto.UserAuthDto(u.username, u.email, u.password, u.role) FROM User u WHERE u.username = :username")
    UserAuthDto findByUsername(String username) throws UsernameNotFoundException;
}
