package com.cooperativevoting.infrastructure.adapters.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public class SessaoRequest {
    @NotBlank(message = "Pauta ID é obrigatório")
    @Schema(description = "ID da pauta que será votada", example = "123e4567-e89b-12d3-a456-426614174000")
    private String pautaId;
    
    @Schema(description = "Duração da sessão em minutos. Se não enviado, será 1 minuto por padrão.", example = "2", nullable = true)
    private Integer duracaoMinutos;

    public String getPautaId() { return pautaId; }
    public void setPautaId(String pautaId) { this.pautaId = pautaId; }
    public Integer getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(Integer duracaoMinutos) { this.duracaoMinutos = duracaoMinutos; }
}
