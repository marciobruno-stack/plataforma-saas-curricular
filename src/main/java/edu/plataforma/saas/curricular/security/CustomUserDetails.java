package edu.plataforma.saas.curricular.security;

import edu.plataforma.saas.curricular.model.Utilizador;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final Utilizador utilizador;

    public CustomUserDetails(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(utilizador.getRole()));
    }

    @Override
    public String getPassword() {
        return utilizador.getPassword();
    }

    @Override
    public String getUsername() {
        return utilizador.getEmail();
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
        return true; // We can add an enabled flag to the entity later if needed
    }
}
