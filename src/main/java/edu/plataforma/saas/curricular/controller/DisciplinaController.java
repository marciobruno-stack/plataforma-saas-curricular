package edu.plataforma.saas.curricular.controller;

import edu.plataforma.saas.curricular.model.Disciplina;
import edu.plataforma.saas.curricular.model.Instituicao;
import edu.plataforma.saas.curricular.model.Utilizador;
import edu.plataforma.saas.curricular.security.SecurityUtils;
import edu.plataforma.saas.curricular.service.DisciplinaService;
import edu.plataforma.saas.curricular.service.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/instituicoes/{instituicaoId}/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private InstituicaoService instituicaoService;

    @Autowired
    private SecurityUtils securityUtils;

    @PostMapping("/nova")
    public String criarDisciplina(@PathVariable Long instituicaoId, @ModelAttribute Disciplina disciplina) {
        Utilizador formador = securityUtils.getCurrentUser();
        Optional<Instituicao> instituicaoOpt = instituicaoService.encontrarPorId(instituicaoId);

        if (instituicaoOpt.isPresent()) {
            Instituicao instituicao = instituicaoOpt.get();
            // Verificar segurança: apenas formadores da escola podem criar disciplinas
            boolean temAcesso = instituicao.getFormadores().stream()
                    .anyMatch(f -> f.getId().equals(formador.getId()));

            if (temAcesso) {
                disciplinaService.criarDisciplina(disciplina, instituicao);
            }
        }

        return "redirect:/instituicoes/" + instituicaoId;
    }
}
