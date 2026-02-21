package br.com.assistencia.os.domain.service;

import br.com.assistencia.os.domain.model.Cliente;
import br.com.assistencia.os.domain.model.Comentario;
import br.com.assistencia.os.domain.model.OrdemServico;
import br.com.assistencia.os.domain.model.StatusOrdemServico;
import br.com.assistencia.os.domain.repository.ClienteRepository;
import br.com.assistencia.os.domain.repository.ComentarioRepository;
import br.com.assistencia.os.domain.repository.OrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class GestaoOrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Transactional
    public OrdemServico criar(OrdemServico ordemServico) {
        Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não Encontrado"));

        ordemServico.setCliente(cliente);
        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(OffsetDateTime.now());

        return ordemServicoRepository.save(ordemServico);
    }

    @Transactional
    public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
        OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada"));

        Comentario comentario = new Comentario();
        comentario.setDataEnvio(OffsetDateTime.now());
        comentario.setDescricao(descricao);
        comentario.setOrdemServico(ordemServico);

        return comentarioRepository.save(comentario);
    }
}
