package com.cooperativevoting.infrastructure.adapters.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public class VotoRequest {
    @NotBlank(message = "CPF do associado é obrigatório")
    private String cpfAssociado;
    
    @NotBlank(message = "Valor do voto é obrigatório (SIM/NAO)")
    private String valor;

    public String getCpfAssociado() { return cpfAssociado; }
    public void setCpfAssociado(String cpfAssociado) { this.cpfAssociado = cpfAssociado; }
    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }
}
