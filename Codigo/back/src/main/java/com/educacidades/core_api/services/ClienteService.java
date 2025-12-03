package com.educacidades.core_api.services;

import com.educacidades.core_api.dto.cliente.ClienteCreateRequestDTO;
import com.educacidades.core_api.dto.cliente.ClienteDTO;
import com.educacidades.core_api.dto.cliente.ClienteUpdateRequestDTO;
import com.educacidades.core_api.models.Cliente;
import com.educacidades.core_api.repositories.ClienteRepository;
import com.educacidades.core_api.repositories.CodigoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final CodigoRepository codigoRepository;

    public List<ClienteDTO> listarTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream().map(Cliente::toDTO).toList();
    }

    private Cliente buscar(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
    }

    public ClienteDTO buscarPorId(Long id) {
        return buscar(id).toDTO();
    }

    @Transactional
    public Cliente salvar(ClienteCreateRequestDTO dto) {
        Long codigo = codigoRepository.getCodigo();

        Cliente cliente = new Cliente(
                codigo,
                dto.razaoSocial(),
                dto.cnpj(),
                dto.email(),
                dto.cidade(),
                dto.estado()
        );

        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente atualizar(Long id, ClienteUpdateRequestDTO dto) {
        Cliente cliente = buscar(id);

        cliente.setRazaoSocial(dto.razaoSocial());
        cliente.setCnpj(dto.cnpj());
        cliente.setEmail(dto.email());
        cliente.setCidade(dto.cidade());
        cliente.setEstado(dto.estado());

        return clienteRepository.save(cliente);
    }

    @Transactional
    public void deletar(Long id) {
        Cliente cliente = buscar(id);
        clienteRepository.delete(cliente);
    }
}
