package com.educacidades.core_api.dto.produto;

import com.educacidades.core_api.models.IMappable;
import com.educacidades.core_api.models.Produto;
import jakarta.validation.constraints.NotBlank;

public record ProdutoPutRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "A descrição é obrigatória")
        String descricao
) implements IMappable<Produto> {}