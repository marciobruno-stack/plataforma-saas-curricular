package edu.plataforma.saas.curricular.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "fichas")
public class Ficha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String descricao;

    // A ligação ao dono da Ficha (Muitas fichas pertencem a Um utilizador)
    @ManyToOne
    @JoinColumn(name = "utilizador_id", nullable = false)
    private Utilizador formador;

    @ManyToMany
    @JoinTable(
            name = "ficha_pergunta", // Nome da tabela intermédia que será criada
            joinColumns = @JoinColumn(name = "ficha_id"),
            inverseJoinColumns = @JoinColumn(name = "pergunta_id")
    )
    private List<Pergunta> perguntas;

    @ManyToMany
    @JoinTable(
            name = "ficha_disciplina",
            joinColumns = @JoinColumn(name = "ficha_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplina_id")
    )
    private List<Disciplina> disciplinasPartilhadas;

    public Ficha() {
    }

    public Ficha(String titulo, String descricao, Utilizador formador) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.formador = formador;
    }

    public Ficha(Ficha original, Utilizador novoDono) {
        this.titulo = "Cópia de " + original.getTitulo();
        this.descricao = original.getDescricao();
        this.formador = novoDono;
        if (original.getPerguntas() != null) {
            this.perguntas = new java.util.ArrayList<>(original.getPerguntas());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Utilizador getFormador() {
        return formador;
    }

    public void setFormador(Utilizador formador) {
        this.formador = formador;
    }

    public List<Pergunta> getPerguntas() {
        return perguntas;
    }

    @Column(name = "codigo_acesso_publico", unique = true)
    private String codigoAcessoPublico;

    @PrePersist
    public void prePersist() {
        if (this.codigoAcessoPublico == null) {
            this.codigoAcessoPublico = java.util.UUID.randomUUID().toString();
        }
    }

    public void setPerguntas(List<Pergunta> perguntas) {
        this.perguntas = perguntas;
    }

    public List<Disciplina> getDisciplinasPartilhadas() {
        return disciplinasPartilhadas;
    }

    public void setDisciplinasPartilhadas(List<Disciplina> disciplinasPartilhadas) {
        this.disciplinasPartilhadas = disciplinasPartilhadas;
    }

    public String getCodigoAcessoPublico() {
        return codigoAcessoPublico;
    }

    public void setCodigoAcessoPublico(String codigoAcessoPublico) {
        this.codigoAcessoPublico = codigoAcessoPublico;
    }
}