package edu.plataforma.saas.curricular.repository;

import edu.plataforma.saas.curricular.model.AnexoPergunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnexoPerguntaRepository extends JpaRepository<AnexoPergunta, Long> {
}
