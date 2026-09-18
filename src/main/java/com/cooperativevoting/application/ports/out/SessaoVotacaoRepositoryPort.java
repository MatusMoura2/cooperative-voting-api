package com.cooperativevoting.application.ports.out;

import com.cooperativevoting.domain.model.SessaoVotacao;
import java.util.Optional;

public interface SessaoVotacaoRepositoryPort {
    SessaoVotacao save(SessaoVotacao sessaoVotacao);
    Optional<SessaoVotacao> findById(String id);
}
