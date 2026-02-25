package br.com.assistencia.os.api.dto;

import br.com.assistencia.os.domain.model.StatusOrdemServico;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class OrdemServicoDTO {

    private Long id;
    private String nomeCliente;
    private String descricao;
    private BigDecimal preco;
    private List<ComentarioDTO> comentarios;
    private StatusOrdemServico status;
    private OffsetDateTime dataAbertura;
    private OffsetDateTime dataFinalizacao;
}
