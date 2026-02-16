package br.com.assistencia.os.domain.service;

import br.com.assistencia.os.domain.model.Cliente;
import br.com.assistencia.os.domain.model.OrdemServico;
import br.com.assistencia.os.domain.model.StatusOrdemServico;
import br.com.assistencia.os.domain.repository.ClienteRepository;
import br.com.assistencia.os.domain.repository.OrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class GestaoOrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public OrdemServico criar(OrdemServico ordemServico) {
        Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não Encontrado"));

        ordemServico.setCliente(cliente);
        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(OffsetDateTime.now());

        return ordemServicoRepository.save(ordemServico);
    }
}
