package br.com.assistencia.os.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ComentarioDTO {

    private Long id;
    private String descricao;
    private OffsetDateTime dataEnvio;
}
