package edu.plataforma.saas.curricular.service;

import edu.plataforma.saas.curricular.model.Pergunta;
import edu.plataforma.saas.curricular.model.Utilizador;
import edu.plataforma.saas.curricular.repository.PerguntaRepository;
import edu.plataforma.saas.curricular.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerguntaService {

    private final PerguntaRepository perguntaRepository;
    private final SecurityUtils securityUtils;

    public PerguntaService(PerguntaRepository perguntaRepository, SecurityUtils securityUtils) {
        this.perguntaRepository = perguntaRepository;
        this.securityUtils = securityUtils;
    }

    public Pergunta guardar(Pergunta pergunta) {
        Utilizador formador = securityUtils.getCurrentUser();
        pergunta.setFormador(formador);
        return perguntaRepository.save(pergunta);
    }

    public List<Pergunta> listar() {
        Utilizador formador = securityUtils.getCurrentUser();
        return perguntaRepository.findByFormador(formador);
    }

    public Optional<Pergunta> encontrarPorId(Long id) {
        return perguntaRepository.findById(id);
    }

    public void apagar(Long id) {
        Optional<Pergunta> perguntaOpt = perguntaRepository.findById(id);
        Utilizador formador = securityUtils.getCurrentUser();
        
        if (perguntaOpt.isPresent() && perguntaOpt.get().getFormador().getId().equals(formador.getId())) {
            perguntaRepository.deleteById(id);
        }
    }

    public void adicionarOpcao(Long perguntaId, String texto, boolean correta) {
        Optional<Pergunta> perguntaOpt = perguntaRepository.findById(perguntaId);
        Utilizador formador = securityUtils.getCurrentUser();

        if (perguntaOpt.isPresent() && perguntaOpt.get().getFormador().getId().equals(formador.getId())) {
            Pergunta pergunta = perguntaOpt.get();
            edu.plataforma.saas.curricular.model.OpcaoResposta opcao = new edu.plataforma.saas.curricular.model.OpcaoResposta(texto, correta, pergunta);
            pergunta.getOpcoes().add(opcao);
            perguntaRepository.save(pergunta);
        }
    }

    public void removerOpcao(Long perguntaId, Long opcaoId) {
        Optional<Pergunta> perguntaOpt = perguntaRepository.findById(perguntaId);
        Utilizador formador = securityUtils.getCurrentUser();

        if (perguntaOpt.isPresent() && perguntaOpt.get().getFormador().getId().equals(formador.getId())) {
            Pergunta pergunta = perguntaOpt.get();
            pergunta.getOpcoes().removeIf(o -> o.getId().equals(opcaoId));
            perguntaRepository.save(pergunta);
        }
    }

    public void adicionarAnexo(Long perguntaId, String nomeOriginal, String caminhoServidor, String tipoConteudo) {
        Optional<Pergunta> perguntaOpt = perguntaRepository.findById(perguntaId);
        Utilizador formador = securityUtils.getCurrentUser();

        if (perguntaOpt.isPresent() && perguntaOpt.get().getFormador().getId().equals(formador.getId())) {
            Pergunta pergunta = perguntaOpt.get();
            edu.plataforma.saas.curricular.model.AnexoPergunta anexo = new edu.plataforma.saas.curricular.model.AnexoPergunta(nomeOriginal, caminhoServidor, tipoConteudo, pergunta);
            pergunta.getAnexos().add(anexo);
            perguntaRepository.save(pergunta);
        }
    }

    public void removerAnexo(Long perguntaId, Long anexoId) {
        Optional<Pergunta> perguntaOpt = perguntaRepository.findById(perguntaId);
        Utilizador formador = securityUtils.getCurrentUser();

        if (perguntaOpt.isPresent() && perguntaOpt.get().getFormador().getId().equals(formador.getId())) {
            Pergunta pergunta = perguntaOpt.get();
            pergunta.getAnexos().removeIf(a -> a.getId().equals(anexoId));
            perguntaRepository.save(pergunta);
        }
    }
}
