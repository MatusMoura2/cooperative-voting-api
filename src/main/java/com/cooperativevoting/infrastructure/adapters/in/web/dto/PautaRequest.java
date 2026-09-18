package com.cooperativevoting.infrastructure.adapters.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public class PautaRequest {
    @NotBlank(message = "Nome é obrigatório")
    @Schema(description = "Nome da pauta", example = "Aprovação do Orçamento 2024")
    private String nome;
    
    @NotBlank(message = "Descrição é obrigatória")
    @Schema(description = "Descrição detalhada sobre a pauta", example = "Votação para definir o orçamento anual.")
    private String descricao;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
