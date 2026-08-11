package edu.plataforma.saas.curricular.repository;

import edu.plataforma.saas.curricular.model.Instituicao;
import edu.plataforma.saas.curricular.model.Utilizador;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class InstituicaoRepositoryTest {

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    @Test
    public void testCriarEEncontrarInstituicao() {
        // 1. Criar e Guardar Instituição
        Instituicao escola = Instituicao.builder()
                .nome("Escola Secundária de Testes")
                .codigoAcesso("TESTE2026")
                .build();
        
        instituicaoRepository.save(escola);

        // 2. Verificar se conseguimos encontrar pelo código
        Optional<Instituicao> escolaEncontrada = instituicaoRepository.findByCodigoAcesso("TESTE2026");
        
        assertThat(escolaEncontrada).isPresent();
        assertThat(escolaEncontrada.get().getNome()).isEqualTo("Escola Secundária de Testes");
    }

    @Test
    public void testLigarFormadorAInstituicao() {
        // 1. Criar Formador e Escola
        Instituicao escola = instituicaoRepository.save(Instituicao.builder()
                .nome("Escola Base")
                .codigoAcesso("BASE123")
                .build());

        Utilizador formador = Utilizador.builder()
                .nome("Professor João")
                .email("joao@escola.pt")
                .password("password123")
                .instituicoes(List.of(escola)) // Ligar a escola ao formador!
                .build();
        
        utilizadorRepository.save(formador);

        // 2. Procurar o formador e verificar se a ligação Many-to-Many funcionou
        Utilizador formadorEncontrado = utilizadorRepository.findByEmail("joao@escola.pt").orElseThrow();
        
        assertThat(formadorEncontrado.getInstituicoes()).hasSize(1);
        assertThat(formadorEncontrado.getInstituicoes().get(0).getCodigoAcesso()).isEqualTo("BASE123");
    }
}
