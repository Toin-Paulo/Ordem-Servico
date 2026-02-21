package br.com.assistencia.os.domain.service;

import br.com.assistencia.os.domain.exception.NegocioException;
import br.com.assistencia.os.domain.model.Cliente;
import br.com.assistencia.os.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
                .stream()
                .anyMatch(clienteExistente -> !clienteExistente.equals(cliente));

        if (emailEmUso) {
            throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
        }
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void excluir(Long clienteId){
        if(!clienteRepository.existsById(clienteId)){
            throw new NegocioException("Cliente não Encontrado.");
        }

        try {
            clienteRepository.deleteById(clienteId);
        } catch (Exception e) {
            throw new NegocioException("o cliente não pode ser removido pois possui ordens de serviço vinculadas.");
        }
    }
}
