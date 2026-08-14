package edu.plataforma.saas.curricular.repository;

import edu.plataforma.saas.curricular.model.Ficha;
import edu.plataforma.saas.curricular.model.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FichaRepository extends JpaRepository<Ficha, Long> {
    List<Ficha> findByFormador(Utilizador formador);
    
    Optional<Ficha> findByCodigoAcessoPublico(String codigoAcessoPublico);
}
