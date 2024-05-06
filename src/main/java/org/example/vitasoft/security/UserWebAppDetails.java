package org.example.vitasoft.security;

import org.example.vitasoft.entity.UserWebApp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserWebAppDetails implements UserDetails {

    private final UserWebApp userWebApp;

    public UserWebAppDetails(UserWebApp userWebApp) {
        this.userWebApp = userWebApp;
    }

    public UserWebApp getUser() {
        return userWebApp;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userWebApp.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }


    public UserWebApp getUserWebApp() {
        return userWebApp;
    }

    @Override
    public String getPassword() {
        return this.userWebApp.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userWebApp.getUsername();
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

