package com.cooperativevoting.infrastructure.adapters.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sessao_votacao")
public class SessaoVotacaoEntity {
    @Id
    private String id;
    private String pautaId;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

    public SessaoVotacaoEntity() {}

    public SessaoVotacaoEntity(String id, String pautaId, LocalDateTime dataInicio, LocalDateTime dataFim) {
        this.id = id;
        this.pautaId = pautaId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
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
