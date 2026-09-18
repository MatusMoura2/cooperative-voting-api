package com.cooperativevoting.domain.model;

public class ResultadoVotacao {
    private String pautaId;
    private long totalSim;
    private long totalNao;

    public ResultadoVotacao(String pautaId, long totalSim, long totalNao) {
        this.pautaId = pautaId;
        this.totalSim = totalSim;
        this.totalNao = totalNao;
    }

    public String getPautaId() { return pautaId; }
    public long getTotalSim() { return totalSim; }
    public long getTotalNao() { return totalNao; }
    
    public String getStatusAprovacao() {
        if (totalSim > totalNao) return "APROVADA";
        if (totalNao > totalSim) return "REJEITADA";
        return "EMPATE";
    }
}
