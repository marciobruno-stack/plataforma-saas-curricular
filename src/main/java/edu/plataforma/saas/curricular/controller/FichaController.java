package edu.plataforma.saas.curricular.controller;

import edu.plataforma.saas.curricular.model.Ficha;
import edu.plataforma.saas.curricular.service.FichaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/fichas")
public class FichaController {

    private final FichaService fichaService;
    private final edu.plataforma.saas.curricular.service.InstituicaoService instituicaoService;
    private final edu.plataforma.saas.curricular.repository.DisciplinaRepository disciplinaRepository;
    private final edu.plataforma.saas.curricular.service.PortalAlunoService portalAlunoService;
    private final edu.plataforma.saas.curricular.service.PerguntaService perguntaService;
    private final edu.plataforma.saas.curricular.repository.PerguntaRepository perguntaRepository;

    public FichaController(FichaService fichaService, 
                           edu.plataforma.saas.curricular.service.InstituicaoService instituicaoService, 
                           edu.plataforma.saas.curricular.repository.DisciplinaRepository disciplinaRepository, 
                           edu.plataforma.saas.curricular.service.PortalAlunoService portalAlunoService,
                           edu.plataforma.saas.curricular.service.PerguntaService perguntaService,
                           edu.plataforma.saas.curricular.repository.PerguntaRepository perguntaRepository) {
        this.fichaService = fichaService;
        this.instituicaoService = instituicaoService;
        this.disciplinaRepository = disciplinaRepository;
        this.portalAlunoService = portalAlunoService;
        this.perguntaService = perguntaService;
        this.perguntaRepository = perguntaRepository;
    }

    @GetMapping
    public String listarFichas(Model model) {
        model.addAttribute("fichas", fichaService.listar());
        return "fichas/lista";
    }

    @GetMapping("/nova")
    public String novaFichaForm(Model model) {
        model.addAttribute("ficha", new Ficha());
        return "fichas/form";
    }

    @PostMapping
    public String salvarNovaFicha(@ModelAttribute Ficha ficha) {
        fichaService.guardar(ficha);
        return "redirect:/fichas?sucesso";
    }

    @GetMapping("/editar/{id}")
    public String editarFichaForm(@PathVariable Long id, Model model) {
        Optional<Ficha> fichaOpt = fichaService.encontrarPorId(id);
        
        if (fichaOpt.isPresent()) {
            model.addAttribute("ficha", fichaOpt.get());
            return "fichas/form";
        }
        return "redirect:/fichas?erro=nao_encontrado";
    }

    @PutMapping("/{id}")
    public String atualizarFicha(@PathVariable Long id, @ModelAttribute Ficha fichaAtualizada) {
        Optional<Ficha> fichaOpt = fichaService.encontrarPorId(id);
        
        if (fichaOpt.isPresent()) {
            Ficha fichaExistente = fichaOpt.get();
            fichaExistente.setTitulo(fichaAtualizada.getTitulo());
            fichaExistente.setDescricao(fichaAtualizada.getDescricao());
            fichaService.guardar(fichaExistente);
            return "redirect:/fichas?sucesso";
        }
        return "redirect:/fichas?erro=nao_encontrado";
    }

    @DeleteMapping("/{id}")
    public String apagarFicha(@PathVariable Long id) {
        fichaService.apagar(id);
        return "redirect:/fichas?apagada";
    }

    @GetMapping("/publicar/{id}")
    public String publicarFichaForm(@PathVariable Long id, Model model) {
        Optional<Ficha> fichaOpt = fichaService.encontrarPorId(id);
        
        if (fichaOpt.isPresent()) {
            model.addAttribute("ficha", fichaOpt.get());
            model.addAttribute("instituicoes", instituicaoService.listar()); 
            return "fichas/publicar";
        }
        return "redirect:/fichas?erro=nao_encontrado";
    }

    @PostMapping("/publicar/{id}")
    public String publicarFichaSubmit(@PathVariable Long id, @RequestParam Long disciplinaId) {
        Optional<edu.plataforma.saas.curricular.model.Disciplina> disciplinaOpt = disciplinaRepository.findById(disciplinaId);
        
        if (disciplinaOpt.isPresent()) {
            fichaService.publicarNaDisciplina(id, disciplinaOpt.get());
            return "redirect:/fichas?publicada";
        }
        return "redirect:/fichas?erro";
    }

    @DeleteMapping("/publicar/{id}/{disciplinaId}")
    public String removerPublicacaoFicha(@PathVariable Long id, @PathVariable Long disciplinaId) {
        Optional<edu.plataforma.saas.curricular.model.Disciplina> disciplinaOpt = disciplinaRepository.findById(disciplinaId);
        if (disciplinaOpt.isPresent()) {
            fichaService.removerPublicacao(id, disciplinaOpt.get());
            return "redirect:/fichas/publicar/" + id + "?removida";
        }
        return "redirect:/fichas?erro";
    }

    @PostMapping("/clonar/{id}")
    public String clonarFicha(@PathVariable Long id) {
        fichaService.clonar(id);
        return "redirect:/fichas?clonada";
    }

    @GetMapping("/{id}/resultados")
    public String verResultados(@PathVariable Long id, Model model) {
        Optional<Ficha> fichaOpt = fichaService.encontrarPorId(id);
        
        if (fichaOpt.isPresent()) {
            model.addAttribute("ficha", fichaOpt.get());
            model.addAttribute("resolucoes", portalAlunoService.listarResolucoes(id));
            return "fichas/resultados";
        }
        return "redirect:/fichas?erro=nao_encontrado";
    }

    @GetMapping("/{id}/perguntas")
    public String associarPerguntasForm(@PathVariable Long id, Model model) {
        Optional<Ficha> fichaOpt = fichaService.encontrarPorId(id);
        
        if (fichaOpt.isPresent()) {
            Ficha ficha = fichaOpt.get();
            model.addAttribute("ficha", ficha);
            // Mostrar todas as perguntas do formador para poder escolher
            model.addAttribute("todasPerguntas", perguntaService.listar());
            return "fichas/perguntas";
        }
        return "redirect:/fichas?erro=nao_encontrado";
    }

    @PostMapping("/{id}/perguntas")
    public String associarPerguntaSubmit(@PathVariable Long id, @RequestParam Long perguntaId) {
        Optional<edu.plataforma.saas.curricular.model.Pergunta> perguntaOpt = perguntaRepository.findById(perguntaId);
        
        if (perguntaOpt.isPresent()) {
            fichaService.adicionarPergunta(id, perguntaOpt.get());
            return "redirect:/fichas/" + id + "/perguntas?adicionada";
        }
        return "redirect:/fichas/" + id + "/perguntas?erro";
    }

    @DeleteMapping("/{id}/perguntas/{perguntaId}")
    public String dissociarPergunta(@PathVariable Long id, @PathVariable Long perguntaId) {
        Optional<edu.plataforma.saas.curricular.model.Pergunta> perguntaOpt = perguntaRepository.findById(perguntaId);
        if (perguntaOpt.isPresent()) {
            fichaService.removerPergunta(id, perguntaOpt.get());
            return "redirect:/fichas/" + id + "/perguntas?removida";
        }
        return "redirect:/fichas/" + id + "/perguntas?erro";
    }
}
