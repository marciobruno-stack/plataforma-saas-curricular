package edu.plataforma.saas.curricular.controller;

import edu.plataforma.saas.curricular.model.Ficha;
import edu.plataforma.saas.curricular.model.Utilizador;
import edu.plataforma.saas.curricular.security.SecurityUtils;
import edu.plataforma.saas.curricular.service.FichaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/fichas")
public class FichaController {

    private final FichaService fichaService;
    private final SecurityUtils securityUtils;
    private final edu.plataforma.saas.curricular.service.InstituicaoService instituicaoService;
    private final edu.plataforma.saas.curricular.repository.DisciplinaRepository disciplinaRepository;
    private final edu.plataforma.saas.curricular.service.PortalAlunoService portalAlunoService;

    public FichaController(FichaService fichaService, SecurityUtils securityUtils, edu.plataforma.saas.curricular.service.InstituicaoService instituicaoService, edu.plataforma.saas.curricular.repository.DisciplinaRepository disciplinaRepository, edu.plataforma.saas.curricular.service.PortalAlunoService portalAlunoService) {
        this.fichaService = fichaService;
        this.securityUtils = securityUtils;
        this.instituicaoService = instituicaoService;
        this.disciplinaRepository = disciplinaRepository;
        this.portalAlunoService = portalAlunoService;
    }

    @GetMapping
    public String listarFichas(Model model) {
        Utilizador formador = securityUtils.getCurrentUser();
        model.addAttribute("fichas", fichaService.listarFichasDoFormador(formador));
        return "fichas/lista";
    }

    @GetMapping("/nova")
    public String novaFichaForm(Model model) {
        model.addAttribute("ficha", new Ficha());
        return "fichas/form";
    }

    @PostMapping("/nova")
    public String salvarNovaFicha(@ModelAttribute Ficha ficha) {
        Utilizador formador = securityUtils.getCurrentUser();
        fichaService.guardarFicha(ficha, formador);
        return "redirect:/fichas?sucesso";
    }

    @GetMapping("/editar/{id}")
    public String editarFichaForm(@PathVariable Long id, Model model) {
        Optional<Ficha> fichaOpt = fichaService.encontrarFichaPorId(id);
        Utilizador formador = securityUtils.getCurrentUser();
        
        // Proteção de segurança: verificar se a ficha existe e pertence a este formador
        if (fichaOpt.isPresent() && fichaOpt.get().getFormador().getId().equals(formador.getId())) {
            model.addAttribute("ficha", fichaOpt.get());
            return "fichas/form";
        }
        return "redirect:/fichas?erro=nao_autorizado";
    }

    @PostMapping("/editar/{id}")
    public String atualizarFicha(@PathVariable Long id, @ModelAttribute Ficha fichaAtualizada) {
        Optional<Ficha> fichaOpt = fichaService.encontrarFichaPorId(id);
        Utilizador formador = securityUtils.getCurrentUser();
        
        if (fichaOpt.isPresent() && fichaOpt.get().getFormador().getId().equals(formador.getId())) {
            Ficha fichaExistente = fichaOpt.get();
            fichaExistente.setTitulo(fichaAtualizada.getTitulo());
            fichaExistente.setDescricao(fichaAtualizada.getDescricao());
            fichaService.guardarFicha(fichaExistente, formador);
            return "redirect:/fichas?sucesso";
        }
        return "redirect:/fichas?erro=nao_autorizado";
    }

    @PostMapping("/apagar/{id}")
    public String apagarFicha(@PathVariable Long id) {
        Optional<Ficha> fichaOpt = fichaService.encontrarFichaPorId(id);
        Utilizador formador = securityUtils.getCurrentUser();
        
        if (fichaOpt.isPresent() && fichaOpt.get().getFormador().getId().equals(formador.getId())) {
            fichaService.apagarFicha(id);
            return "redirect:/fichas?apagada";
        }
        return "redirect:/fichas?erro=nao_autorizado";
    }

    @GetMapping("/publicar/{id}")
    public String publicarFichaForm(@PathVariable Long id, Model model) {
        Optional<Ficha> fichaOpt = fichaService.encontrarFichaPorId(id);
        Utilizador formador = securityUtils.getCurrentUser();
        
        if (fichaOpt.isPresent() && fichaOpt.get().getFormador().getId().equals(formador.getId())) {
            model.addAttribute("ficha", fichaOpt.get());
            model.addAttribute("instituicoes", instituicaoService.listarInstituicoesDoFormador(formador));
            return "fichas/publicar";
        }
        return "redirect:/fichas?erro=nao_autorizado";
    }

    @PostMapping("/publicar/{id}")
    public String publicarFichaSubmit(@PathVariable Long id, @RequestParam Long disciplinaId) {
        Utilizador formador = securityUtils.getCurrentUser();
        Optional<edu.plataforma.saas.curricular.model.Disciplina> disciplinaOpt = disciplinaRepository.findById(disciplinaId);
        
        if (disciplinaOpt.isPresent()) {
            fichaService.publicarFichaNaDisciplina(id, disciplinaOpt.get(), formador);
            return "redirect:/fichas?publicada";
        }
        return "redirect:/fichas?erro";
    }

    @PostMapping("/clonar/{id}")
    public String clonarFicha(@PathVariable Long id) {
        Utilizador formador = securityUtils.getCurrentUser();
        fichaService.clonarFicha(id, formador);
        return "redirect:/fichas?clonada";
    }

    @GetMapping("/{id}/resultados")
    public String verResultados(@PathVariable Long id, Model model) {
        Optional<Ficha> fichaOpt = fichaService.encontrarFichaPorId(id);
        Utilizador formador = securityUtils.getCurrentUser();
        
        if (fichaOpt.isPresent() && fichaOpt.get().getFormador().getId().equals(formador.getId())) {
            model.addAttribute("ficha", fichaOpt.get());
            model.addAttribute("resolucoes", portalAlunoService.listarResolucoesDaFicha(id));
            return "fichas/resultados";
        }
        return "redirect:/fichas?erro=nao_autorizado";
    }
}
