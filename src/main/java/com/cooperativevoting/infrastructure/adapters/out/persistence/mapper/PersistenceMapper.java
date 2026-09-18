package com.cooperativevoting.infrastructure.adapters.out.persistence.mapper;

import com.cooperativevoting.domain.model.Pauta;
import com.cooperativevoting.domain.model.SessaoVotacao;
import com.cooperativevoting.domain.model.Voto;
import com.cooperativevoting.infrastructure.adapters.out.persistence.entity.PautaEntity;
import com.cooperativevoting.infrastructure.adapters.out.persistence.entity.SessaoVotacaoEntity;
import com.cooperativevoting.infrastructure.adapters.out.persistence.entity.VotoEntity;
import org.springframework.stereotype.Component;

@Component
public class PersistenceMapper {

    public PautaEntity toEntity(Pauta pauta) {
        if (pauta == null) return null;
        return new PautaEntity(pauta.getId(), pauta.getNome(), pauta.getDescricao());
    }

    public Pauta toDomain(PautaEntity entity) {
        if (entity == null) return null;
        return new Pauta(entity.getId(), entity.getNome(), entity.getDescricao());
    }

    public SessaoVotacaoEntity toEntity(SessaoVotacao sessao) {
        if (sessao == null) return null;
        return new SessaoVotacaoEntity(sessao.getId(), sessao.getPautaId(), sessao.getDataInicio(), sessao.getDataFim());
    }

    public SessaoVotacao toDomain(SessaoVotacaoEntity entity) {
        if (entity == null) return null;
        return new SessaoVotacao(entity.getId(), entity.getPautaId(), entity.getDataInicio(), entity.getDataFim());
    }

    public VotoEntity toEntity(Voto voto) {
        if (voto == null) return null;
        return new VotoEntity(voto.getId(), voto.getSessaoId(), voto.getAssociadoId(), voto.getValor());
    }

    public Voto toDomain(VotoEntity entity) {
        if (entity == null) return null;
        return new Voto(entity.getId(), entity.getSessaoId(), entity.getAssociadoId(), entity.getValor());
    }
}
