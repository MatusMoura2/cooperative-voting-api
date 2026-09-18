package com.cooperativevoting.infrastructure.adapters.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public class SessaoRequest {
    @NotBlank(message = "Pauta ID é obrigatório")
    private String pautaId;
    
    private Integer duracaoMinutos;

    public String getPautaId() { return pautaId; }
    public void setPautaId(String pautaId) { this.pautaId = pautaId; }
    public Integer getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(Integer duracaoMinutos) { this.duracaoMinutos = duracaoMinutos; }
}
