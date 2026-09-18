package com.cooperativevoting.infrastructure.adapters.out.persistence.entity;

import com.cooperativevoting.domain.model.VotoValor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.PrePersist;
import java.util.UUID;

@Entity
@Table(name = "voto")
public class VotoEntity {
    @Id
    private String id;
    private String sessaoId;
    private String associadoId;

    @Enumerated(EnumType.STRING)
    private VotoValor valor;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

    public VotoEntity() {}

    public VotoEntity(String id, String sessaoId, String associadoId, VotoValor valor) {
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
