package edu.plataforma.saas.curricular.controller;

import edu.plataforma.saas.curricular.model.Ficha;
import edu.plataforma.saas.curricular.model.Pergunta;
import edu.plataforma.saas.curricular.model.RespostaAluno;
import edu.plataforma.saas.curricular.repository.PerguntaRepository;
import edu.plataforma.saas.curricular.service.PortalAlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/aluno/ficha")
public class PortalAlunoController {

    @Autowired
    private PortalAlunoService portalAlunoService;
    
    @Autowired
    private PerguntaRepository perguntaRepository;

    // 1. Landing Page (Insere o Nome)
    @GetMapping("/{codigo}")
    public String landingPage(@PathVariable String codigo, Model model) {
        Optional<Ficha> fichaOpt = portalAlunoService.encontrarPorCodigoPublico(codigo);
        
        if (fichaOpt.isPresent()) {
            model.addAttribute("ficha", fichaOpt.get());
            model.addAttribute("codigo", codigo);
            return "aluno/landing";
        }
        return "error/404";
    }

    // 2. Interface de Resolução (Split-Screen)
    @PostMapping("/{codigo}/iniciar")
    public String iniciarResolucao(@PathVariable String codigo, @RequestParam String nomeAluno, Model model) {
        Optional<Ficha> fichaOpt = portalAlunoService.encontrarPorCodigoPublico(codigo);
        
        if (fichaOpt.isPresent()) {
            model.addAttribute("ficha", fichaOpt.get());
            model.addAttribute("codigo", codigo);
            model.addAttribute("nomeAluno", nomeAluno);
            // Guarda a hora de início em timestamp no HTML para o JS calcular o tempo
            model.addAttribute("startTime", System.currentTimeMillis());
            return "aluno/resolver";
        }
        return "error/404";
    }

    // 3. Receber as Respostas
    @PostMapping("/{codigo}/submeter")
    public String submeterRespostas(
            @PathVariable String codigo,
            @RequestParam String nomeAluno,
            @RequestParam Integer tempoGastoSegundos,
            @RequestParam Map<String, String> allParams,
            Model model) {
            
        List<RespostaAluno> respostas = new ArrayList<>();
        
        // As respostas vêm no formato "resposta_perguntaId"
        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            if (entry.getKey().startsWith("resposta_")) {
                Long perguntaId = Long.parseLong(entry.getKey().replace("resposta_", ""));
                Optional<Pergunta> p = perguntaRepository.findById(perguntaId);
                if (p.isPresent()) {
                    RespostaAluno r = new RespostaAluno();
                    r.setPergunta(p.get());
                    r.setTextoResposta(entry.getValue());
                    respostas.add(r);
                }
            }
        }

        try {
            portalAlunoService.submeterResolucao(codigo, nomeAluno, tempoGastoSegundos, respostas);
            return "aluno/sucesso";
        } catch (Exception e) {
            return "error/500";
        }
    }
}
