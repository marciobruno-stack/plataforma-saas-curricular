package edu.plataforma.saas.curricular.controller;

import edu.plataforma.saas.curricular.model.Disciplina;
import edu.plataforma.saas.curricular.model.Instituicao;
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

    @GetMapping
    public String listarInstituicoes(Model model) {
        List<Instituicao> instituicoes = instituicaoService.listar();
        model.addAttribute("instituicoes", instituicoes);
        return "instituicoes/lista";
    }

    @GetMapping("/nova")
    public String novaInstituicaoForm(Model model) {
        model.addAttribute("instituicao", new Instituicao());
        return "instituicoes/form";
    }

    @PostMapping
    public String criarInstituicao(@ModelAttribute Instituicao instituicao) {
        instituicaoService.criar(instituicao);
        return "redirect:/instituicoes";
    }

    @GetMapping("/aderir")
    public String aderirInstituicaoForm() {
        return "instituicoes/aderir";
    }

    @PostMapping("/aderir")
    public String aderirInstituicao(@RequestParam String codigoAcesso, RedirectAttributes redirectAttributes) {
        boolean sucesso = instituicaoService.aderir(codigoAcesso);
        
        if (sucesso) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Aderiu à Instituição com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", "Código inválido ou já pertence a esta instituição.");
        }
        
        return "redirect:/instituicoes";
    }

    @GetMapping("/{id}")
    public String verInstituicao(@PathVariable Long id, Model model) {
        Optional<Instituicao> instituicaoOpt = instituicaoService.encontrar(id);
        
        if (instituicaoOpt.isPresent()) {
            model.addAttribute("instituicao", instituicaoOpt.get());
            model.addAttribute("novaDisciplina", new Disciplina());
            return "instituicoes/detalhe";
        }
        
        return "redirect:/instituicoes";
    }

    @GetMapping("/{id}/administradores")
    public String gerirAdministradores(@PathVariable Long id, Model model) {
        Optional<Instituicao> instituicaoOpt = instituicaoService.encontrar(id);
        
        if (instituicaoOpt.isPresent()) {
            model.addAttribute("instituicao", instituicaoOpt.get());
            return "instituicoes/administradores";
        }
        
        return "redirect:/instituicoes";
    }

    @PostMapping("/{id}/administradores")
    public String adicionarAdministrador(@PathVariable Long id, @RequestParam String email, RedirectAttributes redirectAttributes) {
        boolean sucesso = instituicaoService.adicionarAdministrador(id, email);
        if (sucesso) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Administrador adicionado com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao adicionar administrador. Verifique se o e-mail está correto e registado.");
        }
        return "redirect:/instituicoes/" + id + "/administradores";
    }
}
