package auth.app.auth_service.jwt;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import auth.app.auth_service.domain.dto.UserAuthDto;

public class UserCustomDetails implements UserDetails {

    private UserAuthDto userAuth;

    public UserCustomDetails(UserAuthDto userAuth) {
        this.userAuth = userAuth;
    }

    @Override
    public String getUsername() {
        return userAuth.getUsername();
    }

    @Override
    public String getPassword() {
        return userAuth.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(userAuth.getRole().name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}
