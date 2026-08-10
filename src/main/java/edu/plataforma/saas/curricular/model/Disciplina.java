package edu.plataforma.saas.curricular.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "disciplinas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "ano_escolaridade", nullable = false, length = 50)
    private String anoEscolaridade;

    @ManyToOne(optional = false)
    @JoinColumn(name = "instituicao_id", nullable = false)
    private Instituicao instituicao;

    @ManyToMany(mappedBy = "disciplinasPartilhadas")
    private List<Ficha> fichasPartilhadas;
}
