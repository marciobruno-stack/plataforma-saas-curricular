package edu.plataforma.saas.curricular.security;

import edu.plataforma.saas.curricular.model.Utilizador;
import edu.plataforma.saas.curricular.repository.UtilizadorRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private final UtilizadorRepository utilizadorRepository;

    public SecurityUtils(UtilizadorRepository utilizadorRepository) {
        this.utilizadorRepository = utilizadorRepository;
    }

    public Utilizador getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // Usa o getName() que devolve o email (tanto no CustomUserDetails como num Mock de Testes)
            String email = authentication.getName();
            return utilizadorRepository.findByEmail(email).orElse(null);
        }
        return null;
    }
}
