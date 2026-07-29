package edu.plataforma.saas.curricular.service;

import edu.plataforma.saas.curricular.model.Pergunta;
import edu.plataforma.saas.curricular.model.Utilizador;
import edu.plataforma.saas.curricular.repository.PerguntaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerguntaService {

    private final PerguntaRepository perguntaRepository;

    public PerguntaService(PerguntaRepository perguntaRepository) {
        this.perguntaRepository = perguntaRepository;
    }

    public Pergunta guardarPergunta(Pergunta pergunta, Utilizador formador) {
        pergunta.setFormador(formador);
        return perguntaRepository.save(pergunta);
    }

    public List<Pergunta> listarPerguntasDoFormador(Utilizador formador) {
        return perguntaRepository.findByFormador(formador);
    }

    public Optional<Pergunta> encontrarPerguntaPorId(Long id) {
        return perguntaRepository.findById(id);
    }

    public void apagarPergunta(Long id) {
        perguntaRepository.deleteById(id);
    }
}
