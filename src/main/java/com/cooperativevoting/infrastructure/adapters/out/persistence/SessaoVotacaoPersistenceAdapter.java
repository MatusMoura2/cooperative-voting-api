package com.cooperativevoting.infrastructure.adapters.out.persistence;

import com.cooperativevoting.application.ports.out.SessaoVotacaoRepositoryPort;
import com.cooperativevoting.domain.model.SessaoVotacao;
import com.cooperativevoting.infrastructure.adapters.out.persistence.entity.SessaoVotacaoEntity;
import com.cooperativevoting.infrastructure.adapters.out.persistence.mapper.PersistenceMapper;
import com.cooperativevoting.infrastructure.adapters.out.persistence.repository.SpringDataSessaoVotacaoRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class SessaoVotacaoPersistenceAdapter implements SessaoVotacaoRepositoryPort {

    private final SpringDataSessaoVotacaoRepository repository;
    private final PersistenceMapper mapper;

    public SessaoVotacaoPersistenceAdapter(SpringDataSessaoVotacaoRepository repository, PersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public SessaoVotacao save(SessaoVotacao sessao) {
        SessaoVotacaoEntity entity = mapper.toEntity(sessao);
        SessaoVotacaoEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<SessaoVotacao> findById(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
