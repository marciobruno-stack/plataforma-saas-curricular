package edu.plataforma.saas.curricular.service;

import edu.plataforma.saas.curricular.model.Ficha;
import edu.plataforma.saas.curricular.model.ResolucaoFicha;
import edu.plataforma.saas.curricular.model.RespostaAluno;
import edu.plataforma.saas.curricular.repository.FichaRepository;
import edu.plataforma.saas.curricular.repository.ResolucaoFichaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PortalAlunoService {

    @Autowired
    private FichaRepository fichaRepository;

    @Autowired
    private ResolucaoFichaRepository resolucaoRepository;

    public Optional<Ficha> encontrarFichaPorCodigoPublico(String codigo) {
        return fichaRepository.findByCodigoAcessoPublico(codigo);
    }

    @Transactional
    public ResolucaoFicha submeterResolucao(String codigoFicha, String nomeAluno, Integer tempoGasto, List<RespostaAluno> respostas) {
        Optional<Ficha> fichaOpt = fichaRepository.findByCodigoAcessoPublico(codigoFicha);
        
        if (fichaOpt.isPresent()) {
            Ficha ficha = fichaOpt.get();
            
            ResolucaoFicha resolucao = new ResolucaoFicha();
            resolucao.setFicha(ficha);
            resolucao.setNomeAluno(nomeAluno);
            resolucao.setTempoGastoSegundos(tempoGasto);
            resolucao.setDataResolucao(LocalDateTime.now());
            
            // Ligar respostas à resolução
            for (RespostaAluno r : respostas) {
                r.setResolucao(resolucao);
            }
            resolucao.setRespostas(respostas);
            
            return resolucaoRepository.save(resolucao);
        }
        throw new RuntimeException("Ficha não encontrada");
    }

    public List<ResolucaoFicha> listarResolucoesDaFicha(Long fichaId) {
        return resolucaoRepository.findByFichaIdOrderByDataResolucaoDesc(fichaId);
    }
}
