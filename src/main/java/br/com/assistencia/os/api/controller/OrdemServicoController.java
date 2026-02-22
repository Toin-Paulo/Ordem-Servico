package br.com.assistencia.os.api.controller;

import br.com.assistencia.os.api.dto.OrdemServicoDTO;
import br.com.assistencia.os.api.input.ComentarioInput;
import br.com.assistencia.os.api.input.OrdemServicoInput;
import br.com.assistencia.os.domain.model.Cliente;
import br.com.assistencia.os.domain.model.Comentario;
import br.com.assistencia.os.domain.model.OrdemServico;
import br.com.assistencia.os.domain.repository.OrdemServicoRepository;
import br.com.assistencia.os.domain.service.GestaoOrdemServicoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private GestaoOrdemServicoService gestaoOrdemServicoService;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoDTO criar(@Valid @RequestBody OrdemServicoInput   ordemServicoInput) {

        OrdemServico novaOrdem = toEntity(ordemServicoInput);

        OrdemServico ordemCriada = gestaoOrdemServicoService.criar(novaOrdem);

        return toDTO(ordemCriada);
    }

    @GetMapping
    public List<OrdemServicoDTO> listar() {
        return toCollectionDTO(ordemServicoRepository.findAll());
    }

    @GetMapping("/{ordemServicoId}")
    public ResponseEntity<OrdemServicoDTO> buscar(@PathVariable Long ordemServicoId) {
        return ordemServicoRepository.findById(ordemServicoId)
                .map(ordemServico -> ResponseEntity.ok(toDTO(ordemServico)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{ordemServicoId}/comentarios")
    @ResponseStatus(HttpStatus.CREATED)
    public Comentario adicionarComentario(@PathVariable Long ordemServicoId,@Valid @RequestBody ComentarioInput comentarioInput) {
        Comentario comentario = gestaoOrdemServicoService.adicionarComentario(ordemServicoId,comentarioInput.getDescricao());
        return toComentarioDTO(comentario);
    }

    @DeleteMapping("/{ordemServicoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable long ordemServicoId) {
        if (!ordemServicoRepository.existsById(ordemServicoId)) {
            throw new EntityNotFoundException("Ordem de serviço não Encontrada!");
        }
        ordemServicoRepository.deleteById(ordemServicoId);
    }

    @PutMapping("/{ordemServicoId}/finalizacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable long ordemServicoId) {
        gestaoOrdemServicoService.finalizar(ordemServicoId);
    }

    @PutMapping("/{ordemServicoId}/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable long ordemServicoId) {
        gestaoOrdemServicoService.cancelar(ordemServicoId);
    }

    private List<OrdemServicoDTO> toCollectionDTO(List<OrdemServico> ordensServico) {
        return ordensServico.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private OrdemServico toEntity(OrdemServicoInput  ordemServicoInput) {

        OrdemServico ordemServico = new OrdemServico();

        ordemServico.setDescricao(ordemServicoInput.getDescricao());
        ordemServico.setPreco(ordemServicoInput.getPreco());

        Cliente cliente = new Cliente();
        cliente.setId(ordemServicoInput.getCliente().getId());

        ordemServico.setCliente(cliente);

        return ordemServico;
    }

    private OrdemServicoDTO toDTO(OrdemServico os) {

        OrdemServicoDTO dto = new OrdemServicoDTO();
        dto.setId(os.getId());
        dto.setNomeCliente(os.getCliente().getNome());
        dto.setDescricao(os.getDescricao());
        dto.setPreco(os.getPreco());
        dto.setStatus(os.getStatus());
        dto.setDataAbertura(os.getDataAbertura());

        return dto;
    }

    private Comentario toComentarioDTO(Comentario comentario) {
        Comentario dto = new Comentario();
        dto.setId(comentario.getId());
        dto.setDescricao(comentario.getDescricao());
        dto.setDataEnvio(comentario.getDataEnvio());

        return dto;
    }

}
