package br.com.assistencia.os.api.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentarioInput {
    @NotNull
    private String descricao;
}
