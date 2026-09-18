package com.cooperativevoting.application.ports.out;

import com.cooperativevoting.domain.model.Pauta;
import java.util.Optional;

public interface PautaRepositoryPort {
    Pauta save(Pauta pauta);
    Optional<Pauta> findById(String id);
}
