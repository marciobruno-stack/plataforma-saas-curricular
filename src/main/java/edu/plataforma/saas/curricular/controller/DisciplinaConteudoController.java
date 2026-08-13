package edu.plataforma.saas.curricular.controller;

import edu.plataforma.saas.curricular.model.Disciplina;
import edu.plataforma.saas.curricular.model.Instituicao;
import edu.plataforma.saas.curricular.model.Utilizador;
import edu.plataforma.saas.curricular.repository.DisciplinaRepository;
import edu.plataforma.saas.curricular.security.SecurityUtils;
import edu.plataforma.saas.curricular.service.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/instituicoes/disciplinas")
public class DisciplinaConteudoController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;
    
    @Autowired
    private InstituicaoService instituicaoService;

    @Autowired
    private SecurityUtils securityUtils;

    @GetMapping("/{id}/fichas")
    public String listarFichasDaDisciplina(@PathVariable Long id, Model model) {
        Utilizador formador = securityUtils.getCurrentUser();
        Optional<Disciplina> disciplinaOpt = disciplinaRepository.findById(id);

        if (disciplinaOpt.isPresent()) {
            Disciplina disciplina = disciplinaOpt.get();
            Instituicao instituicao = disciplina.getInstituicao();
            
            // Segurança: O formador tem de pertencer à instituição desta disciplina
            boolean temAcesso = instituicao.getFormadores().stream()
                    .anyMatch(f -> f.getId().equals(formador.getId()));

            if (temAcesso) {
                model.addAttribute("disciplina", disciplina);
                model.addAttribute("fichas", disciplina.getFichasPartilhadas());
                return "disciplinas/conteudo";
            }
        }
        return "redirect:/instituicoes";
    }
}
