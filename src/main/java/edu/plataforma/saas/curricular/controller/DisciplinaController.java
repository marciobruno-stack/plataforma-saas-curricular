package edu.plataforma.saas.curricular.controller;

import edu.plataforma.saas.curricular.model.Disciplina;
import edu.plataforma.saas.curricular.model.Instituicao;
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

    @PostMapping
    public String criarDisciplina(@PathVariable Long instituicaoId, @ModelAttribute Disciplina disciplina) {
        Optional<Instituicao> instituicaoOpt = instituicaoService.encontrar(instituicaoId);

        if (instituicaoOpt.isPresent()) {
            disciplinaService.criar(disciplina, instituicaoOpt.get());
        }

        return "redirect:/instituicoes/" + instituicaoId;
    }
}
