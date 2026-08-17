package edu.plataforma.saas.curricular.service;

import edu.plataforma.saas.curricular.model.Disciplina;
import edu.plataforma.saas.curricular.model.Instituicao;
import edu.plataforma.saas.curricular.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public Disciplina criar(Disciplina disciplina, Instituicao instituicao) {
        disciplina.setInstituicao(instituicao);
        return disciplinaRepository.save(disciplina);
    }

    public List<Disciplina> listarPorInstituicao(Long instituicaoId) {
        return disciplinaRepository.findByInstituicaoId(instituicaoId);
    }
}
