package edu.plataforma.saas.curricular.repository;

import edu.plataforma.saas.curricular.model.ResolucaoFicha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResolucaoFichaRepository extends JpaRepository<ResolucaoFicha, Long> {
    List<ResolucaoFicha> findByFichaIdOrderByDataResolucaoDesc(Long fichaId);
}
