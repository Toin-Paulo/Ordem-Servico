package br.com.assistencia.os.api.controller;

import br.com.assistencia.os.api.dto.ClienteDTO;
import br.com.assistencia.os.api.input.ClienteInput;
import br.com.assistencia.os.domain.model.Cliente;
import br.com.assistencia.os.domain.repository.ClienteRepository;
import br.com.assistencia.os.domain.service.CadastroClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CadastroClienteService cadastroClienteService;

    @GetMapping
    public List<ClienteDTO> listar() {
        return clienteRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO adicionar(@Valid @RequestBody ClienteInput clienteInput) {
        Cliente novoCliente = toEntity(clienteInput);
        Cliente clienteSalvo = cadastroClienteService.salvar(novoCliente);

        return toDTO(clienteSalvo);
    }

    @DeleteMapping("/{clienteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long clienteId) {
        cadastroClienteService.excluir(clienteId);
    }

    private ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setTelefone(cliente.getTelefone());
        return dto;
    }

    private Cliente toEntity(ClienteInput input) {
        Cliente cliente = new Cliente();
        cliente.setNome(input.getNome());
        cliente.setEmail(input.getEmail());
        cliente.setTelefone(input.getTelefone());
        return cliente;
    }
}
