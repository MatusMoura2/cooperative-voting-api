package com.cooperativevoting.infrastructure.adapters.in.web.dto;

import com.cooperativevoting.domain.model.Pauta;

public class PautaResponse {
    private String id;
    private String nome;
    private String descricao;

    public PautaResponse(Pauta pauta) {
        this.id = pauta.getId();
        this.nome = pauta.getNome();
        this.descricao = pauta.getDescricao();
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
}
