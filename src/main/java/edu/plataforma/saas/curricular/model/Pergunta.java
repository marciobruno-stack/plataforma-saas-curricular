package edu.plataforma.saas.curricular.model;


import jakarta.persistence.*;
import java.util.List;
@Entity
@Table(name = "perguntas")


public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String enunciado;

    @ManyToOne
    @JoinColumn(name = "utilizador_id", nullable = false)
    private Utilizador formador;

    @ManyToMany(mappedBy = "perguntas")
    private List<Ficha> fichas;

    @Column(nullable = false)
    private String tipo;

    public Pergunta() {
    }

    public Pergunta(String enunciado, Utilizador formador, String tipo) {
        this.enunciado = enunciado;
        this.formador = formador;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public Utilizador getFormador() {
        return formador;
    }

    public void setFormador(Utilizador formador) {
        this.formador = formador;
    }

    public List<Ficha> getFichas() {
        return fichas;
    }

    public void setFichas(List<Ficha> fichas) {
        this.fichas = fichas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoFormatado() {
        if (tipo == null) return "";
        return switch (tipo) {
            case "TEXTO_LIVRE" -> "Texto Livre";
            case "ESCOLHA_MULTIPLA" -> "Escolha Múltipla";
            case "VERDADEIRO_FALSO" -> "Verdadeiro / Falso";
            default -> tipo;
        };
    }
}
