package edu.plataforma.saas.curricular.controller;

import edu.plataforma.saas.curricular.model.Pergunta;
import edu.plataforma.saas.curricular.model.Utilizador;
import edu.plataforma.saas.curricular.security.SecurityUtils;
import edu.plataforma.saas.curricular.service.PerguntaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/perguntas")
public class PerguntaController {

    private final PerguntaService perguntaService;

    public PerguntaController(PerguntaService perguntaService) {
        this.perguntaService = perguntaService;
    }

    @GetMapping
    public String listarPerguntas(Model model) {
        Utilizador formador = SecurityUtils.getCurrentUser();
        model.addAttribute("perguntas", perguntaService.listarPerguntasDoFormador(formador));
        return "perguntas/lista";
    }

    @GetMapping("/nova")
    public String novaPerguntaForm(Model model) {
        model.addAttribute("pergunta", new Pergunta());
        return "perguntas/form";
    }

    @PostMapping("/nova")
    public String salvarNovaPergunta(@ModelAttribute Pergunta pergunta) {
        Utilizador formador = SecurityUtils.getCurrentUser();
        perguntaService.guardarPergunta(pergunta, formador);
        return "redirect:/perguntas?sucesso";
    }

    @GetMapping("/editar/{id}")
    public String editarPerguntaForm(@PathVariable Long id, Model model) {
        Optional<Pergunta> perguntaOpt = perguntaService.encontrarPerguntaPorId(id);
        Utilizador formador = SecurityUtils.getCurrentUser();
        
        if (perguntaOpt.isPresent() && perguntaOpt.get().getFormador().getId().equals(formador.getId())) {
            model.addAttribute("pergunta", perguntaOpt.get());
            return "perguntas/form";
        }
        return "redirect:/perguntas?erro=nao_autorizado";
    }

    @PostMapping("/editar/{id}")
    public String atualizarPergunta(@PathVariable Long id, @ModelAttribute Pergunta perguntaAtualizada) {
        Optional<Pergunta> perguntaOpt = perguntaService.encontrarPerguntaPorId(id);
        Utilizador formador = SecurityUtils.getCurrentUser();
        
        if (perguntaOpt.isPresent() && perguntaOpt.get().getFormador().getId().equals(formador.getId())) {
            Pergunta perguntaExistente = perguntaOpt.get();
            perguntaExistente.setEnunciado(perguntaAtualizada.getEnunciado());
            perguntaExistente.setTipo(perguntaAtualizada.getTipo());
            perguntaService.guardarPergunta(perguntaExistente, formador);
            return "redirect:/perguntas?sucesso";
        }
        return "redirect:/perguntas?erro=nao_autorizado";
    }

    @PostMapping("/apagar/{id}")
    public String apagarPergunta(@PathVariable Long id) {
        Optional<Pergunta> perguntaOpt = perguntaService.encontrarPerguntaPorId(id);
        Utilizador formador = SecurityUtils.getCurrentUser();
        
        if (perguntaOpt.isPresent() && perguntaOpt.get().getFormador().getId().equals(formador.getId())) {
            perguntaService.apagarPergunta(id);
            return "redirect:/perguntas?apagada";
        }
        return "redirect:/perguntas?erro=nao_autorizado";
    }
}
