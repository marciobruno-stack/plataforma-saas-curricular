package edu.plataforma.saas.curricular.model;

import jakarta.persistence.*;

@Entity
@Table(name = "respostas_aluno")
public class RespostaAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String textoResposta;

    @ManyToOne
    @JoinColumn(name = "resolucao_id", nullable = false)
    private ResolucaoFicha resolucao;

    @ManyToOne
    @JoinColumn(name = "pergunta_id", nullable = false)
    private Pergunta pergunta;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTextoResposta() { return textoResposta; }
    public void setTextoResposta(String textoResposta) { this.textoResposta = textoResposta; }
    
    public ResolucaoFicha getResolucao() { return resolucao; }
    public void setResolucao(ResolucaoFicha resolucao) { this.resolucao = resolucao; }
    
    public Pergunta getPergunta() { return pergunta; }
    public void setPergunta(Pergunta pergunta) { this.pergunta = pergunta; }
}
