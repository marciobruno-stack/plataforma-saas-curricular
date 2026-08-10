package edu.plataforma.saas.curricular.repository;

import edu.plataforma.saas.curricular.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    List<Disciplina> findByInstituicaoId(Long instituicaoId);
}
