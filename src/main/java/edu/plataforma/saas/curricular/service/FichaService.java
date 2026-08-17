package edu.plataforma.saas.curricular.service;

import edu.plataforma.saas.curricular.model.Ficha;
import edu.plataforma.saas.curricular.model.Utilizador;
import edu.plataforma.saas.curricular.repository.FichaRepository;
import edu.plataforma.saas.curricular.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FichaService {

    private final FichaRepository fichaRepository;
    private final SecurityUtils securityUtils;

    public FichaService(FichaRepository fichaRepository, SecurityUtils securityUtils) {
        this.fichaRepository = fichaRepository;
        this.securityUtils = securityUtils;
    }

    public Ficha guardar(Ficha ficha) {
        Utilizador formador = securityUtils.getCurrentUser();
        ficha.setFormador(formador);
        return fichaRepository.save(ficha);
    }

    public List<Ficha> listar() {
        Utilizador formador = securityUtils.getCurrentUser();
        return fichaRepository.findByFormador(formador);
    }

    public Optional<Ficha> encontrarPorId(Long id) {
        return fichaRepository.findById(id);
    }

    public void apagar(Long id) {
        Optional<Ficha> fichaOpt = fichaRepository.findById(id);
        Utilizador formador = securityUtils.getCurrentUser();
        
        if (fichaOpt.isPresent() && fichaOpt.get().getFormador().getId().equals(formador.getId())) {
            fichaRepository.deleteById(id);
        }
    }

    public void publicarNaDisciplina(Long fichaId, edu.plataforma.saas.curricular.model.Disciplina disciplina) {
        Optional<Ficha> fichaOpt = fichaRepository.findById(fichaId);
        Utilizador formador = securityUtils.getCurrentUser();
        
        if (fichaOpt.isPresent() && fichaOpt.get().getFormador().getId().equals(formador.getId())) {
            Ficha ficha = fichaOpt.get();
            if (!ficha.getDisciplinasPartilhadas().contains(disciplina)) {
                ficha.getDisciplinasPartilhadas().add(disciplina);
                fichaRepository.save(ficha);
            }
        }
    }

    public void removerPublicacao(Long fichaId, edu.plataforma.saas.curricular.model.Disciplina disciplina) {
        Optional<Ficha> fichaOpt = fichaRepository.findById(fichaId);
        Utilizador formador = securityUtils.getCurrentUser();
        
        if (fichaOpt.isPresent() && fichaOpt.get().getFormador().getId().equals(formador.getId())) {
            Ficha ficha = fichaOpt.get();
            if (ficha.getDisciplinasPartilhadas().contains(disciplina)) {
                ficha.getDisciplinasPartilhadas().remove(disciplina);
                fichaRepository.save(ficha);
            }
        }
    }

    public void clonar(Long fichaOriginalId) {
        Optional<Ficha> fichaOpt = fichaRepository.findById(fichaOriginalId);
        Utilizador novoDono = securityUtils.getCurrentUser();
        
        if (fichaOpt.isPresent()) {
            Ficha fichaOriginal = fichaOpt.get();
            Ficha novaFicha = new Ficha(fichaOriginal, novoDono);
            fichaRepository.save(novaFicha);
        }
    }

    public void adicionarPergunta(Long fichaId, edu.plataforma.saas.curricular.model.Pergunta pergunta) {
        Optional<Ficha> fichaOpt = fichaRepository.findById(fichaId);
        Utilizador formador = securityUtils.getCurrentUser();
        
        if (fichaOpt.isPresent() && fichaOpt.get().getFormador().getId().equals(formador.getId())) {
            Ficha ficha = fichaOpt.get();
            if (!ficha.getPerguntas().contains(pergunta)) {
                ficha.getPerguntas().add(pergunta);
                fichaRepository.save(ficha);
            }
        }
    }

    public void removerPergunta(Long fichaId, edu.plataforma.saas.curricular.model.Pergunta pergunta) {
        Optional<Ficha> fichaOpt = fichaRepository.findById(fichaId);
        Utilizador formador = securityUtils.getCurrentUser();
        
        if (fichaOpt.isPresent() && fichaOpt.get().getFormador().getId().equals(formador.getId())) {
            Ficha ficha = fichaOpt.get();
            if (ficha.getPerguntas().contains(pergunta)) {
                ficha.getPerguntas().remove(pergunta);
                fichaRepository.save(ficha);
            }
        }
    }
}