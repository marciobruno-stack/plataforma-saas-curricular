package edu.plataforma.saas.curricular.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "instituicoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(name = "codigo_acesso", nullable = false, unique = true, length = 20)
    private String codigoAcesso;

    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Disciplina> disciplinas;

    @ManyToMany(mappedBy = "instituicoes")
    private List<Utilizador> formadores;
}
