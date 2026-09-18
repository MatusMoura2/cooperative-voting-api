package com.cooperativevoting.domain.model;

import java.time.LocalDateTime;

public class SessaoVotacao {
    private String id;
    private String pautaId;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    public SessaoVotacao(String id, String pautaId, LocalDateTime dataInicio, LocalDateTime dataFim) {
        this.id = id;
        this.pautaId = pautaId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public boolean isAberta(LocalDateTime agora) {
        return agora.isAfter(dataInicio) && agora.isBefore(dataFim);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPautaId() { return pautaId; }
    public void setPautaId(String pautaId) { this.pautaId = pautaId; }
    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }
    public LocalDateTime getDataFim() { return dataFim; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }
}
