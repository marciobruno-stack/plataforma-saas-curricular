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
}