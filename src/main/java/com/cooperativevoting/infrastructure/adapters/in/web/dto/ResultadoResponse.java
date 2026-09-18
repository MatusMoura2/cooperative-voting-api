package com.cooperativevoting.infrastructure.adapters.in.web.dto;

import com.cooperativevoting.domain.model.ResultadoVotacao;

public class ResultadoResponse {
    private String sessaoId;
    private long totalSim;
    private long totalNao;
    private String statusAprovacao;

    public ResultadoResponse(ResultadoVotacao resultado) {
        this.sessaoId = resultado.getPautaId();
        this.totalSim = resultado.getTotalSim();
        this.totalNao = resultado.getTotalNao();
        this.statusAprovacao = resultado.getStatusAprovacao();
    }

    public String getSessaoId() { return sessaoId; }
    public long getTotalSim() { return totalSim; }
    public long getTotalNao() { return totalNao; }
    public String getStatusAprovacao() { return statusAprovacao; }
}
