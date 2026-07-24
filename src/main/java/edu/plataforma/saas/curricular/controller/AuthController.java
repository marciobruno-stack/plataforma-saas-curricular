package edu.plataforma.saas.curricular.controller;

import edu.plataforma.saas.curricular.model.Utilizador;
import edu.plataforma.saas.curricular.repository.UtilizadorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UtilizadorRepository utilizadorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UtilizadorRepository utilizadorRepository, PasswordEncoder passwordEncoder) {
        this.utilizadorRepository = utilizadorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registo")
    public String registoForm(Model model) {
        model.addAttribute("utilizador", new Utilizador());
        return "registo";
    }

    @PostMapping("/registo")
    public String registoSubmit(@ModelAttribute Utilizador utilizador, Model model) {
        if (utilizadorRepository.existsByEmail(utilizador.getEmail())) {
            model.addAttribute("erro", "Este e-mail já está registado!");
            return "registo";
        }

        // Encripta a password com BCrypt antes de gravar na BD
        utilizador.setPassword(passwordEncoder.encode(utilizador.getPassword()));
        utilizadorRepository.save(utilizador);

        return "redirect:/login?sucesso";
    }
}