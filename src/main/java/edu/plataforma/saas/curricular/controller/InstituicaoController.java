package edu.plataforma.saas.curricular.controller;

import edu.plataforma.saas.curricular.model.Disciplina;
import edu.plataforma.saas.curricular.model.Instituicao;
import edu.plataforma.saas.curricular.model.Utilizador;
import edu.plataforma.saas.curricular.security.SecurityUtils;
import edu.plataforma.saas.curricular.service.DisciplinaService;
import edu.plataforma.saas.curricular.service.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/instituicoes")
public class InstituicaoController {

    @Autowired
    private InstituicaoService instituicaoService;
    
    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private SecurityUtils securityUtils;

    @GetMapping
    public String listarInstituicoes(Model model) {
        Utilizador formador = securityUtils.getCurrentUser();
        List<Instituicao> instituicoes = instituicaoService.listarInstituicoesDoFormador(formador);
        model.addAttribute("instituicoes", instituicoes);
        return "instituicoes/lista";
    }

    @GetMapping("/nova")
    public String novaInstituicaoForm(Model model) {
        model.addAttribute("instituicao", new Instituicao());
        return "instituicoes/form";
    }

    @PostMapping("/nova")
    public String criarInstituicao(@ModelAttribute Instituicao instituicao) {
        Utilizador formador = securityUtils.getCurrentUser();
        instituicaoService.criarInstituicao(instituicao, formador);
        return "redirect:/instituicoes";
    }

    @GetMapping("/aderir")
    public String aderirInstituicaoForm() {
        return "instituicoes/aderir";
    }

    @PostMapping("/aderir")
    public String aderirInstituicao(@RequestParam String codigoAcesso, RedirectAttributes redirectAttributes) {
        Utilizador formador = securityUtils.getCurrentUser();
        boolean sucesso = instituicaoService.aderirAInstituicao(codigoAcesso, formador);
        
        if (sucesso) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Aderiu à Instituição com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", "Código inválido ou já pertence a esta instituição.");
        }
        
        return "redirect:/instituicoes";
    }

    @GetMapping("/{id}")
    public String verInstituicao(@PathVariable Long id, Model model) {
        Utilizador formador = securityUtils.getCurrentUser();
        Optional<Instituicao> instituicaoOpt = instituicaoService.encontrarPorId(id);
        
        if (instituicaoOpt.isPresent()) {
            Instituicao instituicao = instituicaoOpt.get();
            // Verificar se o formador tem acesso a esta instituição
            boolean temAcesso = instituicao.getFormadores().stream()
                    .anyMatch(f -> f.getId().equals(formador.getId()));
                    
            if (temAcesso) {
                model.addAttribute("instituicao", instituicao);
                model.addAttribute("novaDisciplina", new Disciplina());
                return "instituicoes/detalhe";
            }
        }
        
        return "redirect:/instituicoes";
    }
}
