package com.educacidades.core_api.mapper;

import com.educacidades.core_api.dto.cliente.ClienteDTO;
import com.educacidades.core_api.models.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    public ClienteDTO clienteToClienteDTO(Cliente cliente);
}
