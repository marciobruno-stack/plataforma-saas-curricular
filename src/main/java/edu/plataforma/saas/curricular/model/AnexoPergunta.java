package edu.plataforma.saas.curricular.model;

import jakarta.persistence.*;

@Entity
@Table(name = "anexos_pergunta")
public class AnexoPergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeOriginal;

    @Column(nullable = false)
    private String caminhoServidor;

    private String tipoConteudo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pergunta_id", nullable = false)
    private Pergunta pergunta;

    public AnexoPergunta() {
    }

    public AnexoPergunta(String nomeOriginal, String caminhoServidor, String tipoConteudo, Pergunta pergunta) {
        this.nomeOriginal = nomeOriginal;
        this.caminhoServidor = caminhoServidor;
        this.tipoConteudo = tipoConteudo;
        this.pergunta = pergunta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeOriginal() {
        return nomeOriginal;
    }

    public void setNomeOriginal(String nomeOriginal) {
        this.nomeOriginal = nomeOriginal;
    }

    public String getCaminhoServidor() {
        return caminhoServidor;
    }

    public void setCaminhoServidor(String caminhoServidor) {
        this.caminhoServidor = caminhoServidor;
    }

    public String getTipoConteudo() {
        return tipoConteudo;
    }

    public void setTipoConteudo(String tipoConteudo) {
        this.tipoConteudo = tipoConteudo;
    }

    public Pergunta getPergunta() {
        return pergunta;
    }

    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
    }
}
