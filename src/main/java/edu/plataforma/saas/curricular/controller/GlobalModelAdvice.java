package edu.plataforma.saas.curricular.controller;

import edu.plataforma.saas.curricular.model.Instituicao;
import edu.plataforma.saas.curricular.model.Utilizador;
import edu.plataforma.saas.curricular.security.SecurityUtils;
import edu.plataforma.saas.curricular.service.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalModelAdvice {

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private InstituicaoService instituicaoService;

    @ModelAttribute("minhasInstituicoesGlobais")
    public List<Instituicao> globalInstituicoes() {
        try {
            Utilizador currentUser = securityUtils.getCurrentUser();
            if (currentUser != null) {
                return instituicaoService.listarInstituicoesDoFormador(currentUser);
            }
        } catch (Exception e) {
            // Ignorar para requests não autenticados (ex: /login, /aluno/**)
        }
        return new ArrayList<>();
    }
}
