package br.com.assistencia.os.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

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

    private OffsetDateTime dataAbertura;
    private OffsetDateTime dataFinalizacao;
}
