package com.cooperativevoting.infrastructure.adapters.in.web.dto;

import com.cooperativevoting.domain.model.SessaoVotacao;
import java.time.LocalDateTime;

public class SessaoResponse {
    private String id;
    private String pautaId;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    public SessaoResponse(SessaoVotacao sessao) {
        this.id = sessao.getId();
        this.pautaId = sessao.getPautaId();
        this.dataInicio = sessao.getDataInicio();
        this.dataFim = sessao.getDataFim();
    }

    public String getId() { return id; }
    public String getPautaId() { return pautaId; }
    public LocalDateTime getDataInicio() { return dataInicio; }
    public LocalDateTime getDataFim() { return dataFim; }
}
