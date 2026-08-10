package edu.plataforma.saas.curricular.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "utilizadores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilizador {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String role; // Ex: 'ROLE_FORMADOR'

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    protected void onCreate() {
        this.criadoEm = LocalDateTime.now();
        if (this.role == null) {
            this.role = "ROLE_FORMADOR";
        }
    }

    @ManyToMany
    @JoinTable(
            name = "utilizador_instituicao",
            joinColumns = @JoinColumn(name = "utilizador_id"),
            inverseJoinColumns = @JoinColumn(name = "instituicao_id")
    )
    private java.util.List<Instituicao> instituicoes;
}