package edu.plataforma.saas.curricular.repository;

import edu.plataforma.saas.curricular.model.Pergunta;
import edu.plataforma.saas.curricular.model.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
    List<Pergunta> findByFormador(Utilizador formador);

}
