package edu.plataforma.saas.curricular.service;

import edu.plataforma.saas.curricular.model.Instituicao;
import edu.plataforma.saas.curricular.model.Utilizador;
import edu.plataforma.saas.curricular.repository.InstituicaoRepository;
import edu.plataforma.saas.curricular.repository.UtilizadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InstituicaoService {

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    @Transactional
    public Instituicao criarInstituicao(Instituicao instituicao, Utilizador criador) {
        // Gerar um código de acesso único (ex: as 8 primeiras letras de um UUID)
        String codigoAcesso = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        instituicao.setCodigoAcesso(codigoAcesso);
        
        Instituicao novaInstituicao = instituicaoRepository.save(instituicao);
        
        // Adicionar o formador que criou à lista de membros da instituição
        criador.getInstituicoes().add(novaInstituicao);
        utilizadorRepository.save(criador);
        
        return novaInstituicao;
    }

    @Transactional
    public boolean aderirAInstituicao(String codigoAcesso, Utilizador formador) {
        Optional<Instituicao> instituicaoOpt = instituicaoRepository.findByCodigoAcesso(codigoAcesso);
        
        if (instituicaoOpt.isPresent()) {
            Instituicao instituicao = instituicaoOpt.get();
            
            // Verificar se o formador já pertence à instituição
            boolean jaPertence = formador.getInstituicoes().stream()
                    .anyMatch(inst -> inst.getId().equals(instituicao.getId()));
            
            if (!jaPertence) {
                formador.getInstituicoes().add(instituicao);
                utilizadorRepository.save(formador);
                return true;
            }
        }
        return false;
    }

    public List<Instituicao> listarInstituicoesDoFormador(Utilizador formador) {
        return formador.getInstituicoes();
    }

    public Optional<Instituicao> encontrarPorId(Long id) {
        return instituicaoRepository.findById(id);
    }
}
