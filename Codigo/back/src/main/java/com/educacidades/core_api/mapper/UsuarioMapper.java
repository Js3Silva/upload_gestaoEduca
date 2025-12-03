package com.educacidades.core_api.mapper;

import com.educacidades.core_api.dto.usuario.UsuarioDTO;
import com.educacidades.core_api.models.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    public UsuarioDTO usuarioToUsuarioDTO(Usuario usuario);
}
