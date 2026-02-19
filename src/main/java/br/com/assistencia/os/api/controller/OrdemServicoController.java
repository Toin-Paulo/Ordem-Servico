package br.com.assistencia.os.api.controller;

import br.com.assistencia.os.api.dto.OrdemServicoDTO;
import br.com.assistencia.os.api.input.OrdemServicoInput;
import br.com.assistencia.os.domain.model.Cliente;
import br.com.assistencia.os.domain.model.Comentario;
import br.com.assistencia.os.domain.model.OrdemServico;
import br.com.assistencia.os.domain.repository.OrdemServicoRepository;
import br.com.assistencia.os.domain.service.GestaoOrdemServicoService;
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
        List<OrdemServico> ordensServico = ordemServicoRepository.findAll();

        return ordensServico.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{ordemServicoId}")
    public ResponseEntity<OrdemServicoDTO> buscar(@PathVariable long ordemServicoId) {
        return ordemServicoRepository.findById(ordemServicoId)
                .map(ordemServico -> ResponseEntity.ok(toDTO(ordemServico)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{ordemServicoId}/comentarios")
    @ResponseStatus(HttpStatus.CREATED)
    public Comentario adicionarComentario(@PathVariable Long ordemServicoId, @RequestBody String descricao) {
        return gestaoOrdemServicoService.adicionarComentario(ordemServicoId, descricao);
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

}
