package edu.plataforma.saas.curricular.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "resolucoes_ficha")
public class ResolucaoFicha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeAluno;

    private Integer tempoGastoSegundos;

    @Column(nullable = false)
    private LocalDateTime dataResolucao;

    @ManyToOne
    @JoinColumn(name = "ficha_id", nullable = false)
    private Ficha ficha;

    @OneToMany(mappedBy = "resolucao", cascade = CascadeType.ALL)
    private List<RespostaAluno> respostas;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }
    
    public Integer getTempoGastoSegundos() { return tempoGastoSegundos; }
    public void setTempoGastoSegundos(Integer tempoGastoSegundos) { this.tempoGastoSegundos = tempoGastoSegundos; }
    
    public LocalDateTime getDataResolucao() { return dataResolucao; }
    public void setDataResolucao(LocalDateTime dataResolucao) { this.dataResolucao = dataResolucao; }
    
    public Ficha getFicha() { return ficha; }
    public void setFicha(Ficha ficha) { this.ficha = ficha; }

    public List<RespostaAluno> getRespostas() { return respostas; }
    public void setRespostas(List<RespostaAluno> respostas) { this.respostas = respostas; }
}
