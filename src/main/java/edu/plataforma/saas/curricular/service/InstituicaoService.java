package edu.plataforma.saas.curricular.service;

import edu.plataforma.saas.curricular.model.Instituicao;
import edu.plataforma.saas.curricular.model.Utilizador;
import edu.plataforma.saas.curricular.repository.InstituicaoRepository;
import edu.plataforma.saas.curricular.repository.UtilizadorRepository;
import edu.plataforma.saas.curricular.security.SecurityUtils;
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
    
    @Autowired
    private SecurityUtils securityUtils;

    @Transactional
    public Instituicao criar(Instituicao instituicao) {
        Utilizador criador = securityUtils.getCurrentUser();
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
    public boolean aderir(String codigoAcesso) {
        Utilizador formador = securityUtils.getCurrentUser();
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

    public List<Instituicao> listar() {
        Utilizador formador = securityUtils.getCurrentUser();
        return formador.getInstituicoes();
    }

    public Optional<Instituicao> encontrar(Long id) {
        Utilizador formador = securityUtils.getCurrentUser();
        Optional<Instituicao> instituicaoOpt = instituicaoRepository.findById(id);
        if (instituicaoOpt.isPresent()) {
            boolean temAcesso = instituicaoOpt.get().getFormadores().stream()
                    .anyMatch(f -> f.getId().equals(formador.getId()));
            if (temAcesso) {
                return instituicaoOpt;
            }
        }
        return Optional.empty();
    }
    
    // For internal usage where access check isn't needed or is done differently
    public Optional<Instituicao> encontrarPorId(Long id) {
        return instituicaoRepository.findById(id);
    }
}
