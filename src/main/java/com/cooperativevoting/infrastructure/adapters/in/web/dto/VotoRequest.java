package com.cooperativevoting.infrastructure.adapters.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public class VotoRequest {
    @NotBlank(message = "CPF do associado é obrigatório")
    @Schema(description = "CPF do associado, sem pontuação", example = "12345678901")
    private String cpfAssociado;
    
    @NotBlank(message = "Valor do voto é obrigatório (SIM/NAO)")
    @Schema(description = "Valor do voto: SIM ou NAO", example = "SIM")
    private String valor;

    public String getCpfAssociado() { return cpfAssociado; }
    public void setCpfAssociado(String cpfAssociado) { this.cpfAssociado = cpfAssociado; }
    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }
}
