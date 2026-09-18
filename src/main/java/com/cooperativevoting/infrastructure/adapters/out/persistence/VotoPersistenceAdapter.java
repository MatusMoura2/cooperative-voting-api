package com.cooperativevoting.infrastructure.adapters.out.persistence;

import com.cooperativevoting.application.ports.out.VotoRepositoryPort;
import com.cooperativevoting.domain.model.Voto;
import com.cooperativevoting.infrastructure.adapters.out.persistence.entity.VotoEntity;
import com.cooperativevoting.infrastructure.adapters.out.persistence.mapper.PersistenceMapper;
import com.cooperativevoting.infrastructure.adapters.out.persistence.repository.SpringDataVotoRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VotoPersistenceAdapter implements VotoRepositoryPort {

    private final SpringDataVotoRepository repository;
    private final PersistenceMapper mapper;

    public VotoPersistenceAdapter(SpringDataVotoRepository repository, PersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Voto save(Voto voto) {
        VotoEntity entity = mapper.toEntity(voto);
        VotoEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public boolean existsBySessaoIdAndAssociadoId(String sessaoId, String associadoId) {
        return repository.existsBySessaoIdAndAssociadoId(sessaoId, associadoId);
    }

    @Override
    public List<Voto> findBySessaoId(String sessaoId) {
        return repository.findBySessaoId(sessaoId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
