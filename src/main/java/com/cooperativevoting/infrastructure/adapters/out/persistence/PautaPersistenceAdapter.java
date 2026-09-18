package com.cooperativevoting.infrastructure.adapters.out.persistence;

import com.cooperativevoting.application.ports.out.PautaRepositoryPort;
import com.cooperativevoting.domain.model.Pauta;
import com.cooperativevoting.infrastructure.adapters.out.persistence.entity.PautaEntity;
import com.cooperativevoting.infrastructure.adapters.out.persistence.mapper.PersistenceMapper;
import com.cooperativevoting.infrastructure.adapters.out.persistence.repository.SpringDataPautaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class PautaPersistenceAdapter implements PautaRepositoryPort {

    private final SpringDataPautaRepository repository;
    private final PersistenceMapper mapper;

    public PautaPersistenceAdapter(SpringDataPautaRepository repository, PersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Pauta save(Pauta pauta) {
        PautaEntity entity = mapper.toEntity(pauta);
        PautaEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Pauta> findById(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
