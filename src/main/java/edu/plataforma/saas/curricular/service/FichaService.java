package edu.plataforma.saas.curricular.service;

import edu.plataforma.saas.curricular.model.Ficha;
import edu.plataforma.saas.curricular.model.Utilizador;
import edu.plataforma.saas.curricular.repository.FichaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FichaService {

    private final FichaRepository fichaRepository;


    public FichaService(FichaRepository fichaRepository) {
        this.fichaRepository = fichaRepository;
    }

    public Ficha guardarFicha(Ficha ficha, Utilizador formador) {
        ficha.setFormador(formador);
        return fichaRepository.save(ficha);
    }

    public List<Ficha> listarFichasDoFormador(Utilizador formador) {
        return fichaRepository.findByFormador(formador);
    }

    public Optional<Ficha> encontrarFichaPorId(Long id) {
        return fichaRepository.findById(id);
    }

    public void apagarFicha(Long id) {
        fichaRepository.deleteById(id);
    }

    public void publicarFichaNaDisciplina(Long fichaId, edu.plataforma.saas.curricular.model.Disciplina disciplina, Utilizador formador) {
        Optional<Ficha> fichaOpt = fichaRepository.findById(fichaId);
        if (fichaOpt.isPresent() && fichaOpt.get().getFormador().getId().equals(formador.getId())) {
            Ficha ficha = fichaOpt.get();
            if (!ficha.getDisciplinasPartilhadas().contains(disciplina)) {
                ficha.getDisciplinasPartilhadas().add(disciplina);
                fichaRepository.save(ficha);
            }
        }
    }

    public void clonarFicha(Long fichaOriginalId, Utilizador novoDono) {
        Optional<Ficha> fichaOpt = fichaRepository.findById(fichaOriginalId);
        if (fichaOpt.isPresent()) {
            Ficha fichaOriginal = fichaOpt.get();
            
            Ficha novaFicha = new Ficha();
            novaFicha.setTitulo("Cópia de " + fichaOriginal.getTitulo());
            novaFicha.setDescricao(fichaOriginal.getDescricao());
            novaFicha.setFormador(novoDono);
            
            // Copiar as perguntas (Many-to-Many permite referenciar as mesmas)
            if (fichaOriginal.getPerguntas() != null) {
                novaFicha.setPerguntas(new java.util.ArrayList<>(fichaOriginal.getPerguntas()));
            }
            
            fichaRepository.save(novaFicha);
        }
    }
}