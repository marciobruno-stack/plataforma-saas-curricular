package edu.plataforma.saas.curricular.repository;

import edu.plataforma.saas.curricular.model.OpcaoResposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcaoRespostaRepository extends JpaRepository<OpcaoResposta, Long> {
}
