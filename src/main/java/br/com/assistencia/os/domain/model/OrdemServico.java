package br.com.assistencia.os.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    private String descricao;
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    private StatusOrdemServico status;

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL)
    private List<Comentario> comentarios = new ArrayList<>();

    private OffsetDateTime dataAbertura;
    private OffsetDateTime dataFinalizacao;

    public void finalizar() {
        if (!StatusOrdemServico.ABERTA.equals(getStatus())) {
            throw new IllegalStateException("Ordem de serviço não pode ser finalizada.");
        }

        setStatus(StatusOrdemServico.FINALIZADA);
        setDataAbertura(OffsetDateTime.now());
    }

    public void cancelar() {
        if (!StatusOrdemServico.ABERTA.equals(getStatus())) {
            throw new IllegalStateException("Ordem de serviço não pode ser cancelada.");
        }

        setStatus(StatusOrdemServico.CANCELADA);
    }
}
