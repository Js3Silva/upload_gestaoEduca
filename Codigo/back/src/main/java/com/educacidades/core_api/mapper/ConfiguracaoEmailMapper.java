package com.educacidades.core_api.mapper;

import com.educacidades.core_api.dto.configuracao_email.ConfiguracaoEmailDTO;
import com.educacidades.core_api.models.ConfiguracaoEmail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConfiguracaoEmailMapper {
    ConfiguracaoEmailDTO configuracaoEmailToConfiguracaoEmailDTO(ConfiguracaoEmail configuracaoEmail);
}
