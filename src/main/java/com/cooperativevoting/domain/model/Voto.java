package com.cooperativevoting.domain.model;

public class Voto {
    private String id;
    private String sessaoId;
    private String associadoId;
    private VotoValor valor;

    public Voto(String id, String sessaoId, String associadoId, VotoValor valor) {
        this.id = id;
        this.sessaoId = sessaoId;
        this.associadoId = associadoId;
        this.valor = valor;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSessaoId() { return sessaoId; }
    public void setSessaoId(String sessaoId) { this.sessaoId = sessaoId; }
    public String getAssociadoId() { return associadoId; }
    public void setAssociadoId(String associadoId) { this.associadoId = associadoId; }
    public VotoValor getValor() { return valor; }
    public void setValor(VotoValor valor) { this.valor = valor; }
}
