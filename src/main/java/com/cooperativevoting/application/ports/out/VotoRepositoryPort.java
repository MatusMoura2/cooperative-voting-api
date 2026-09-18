package com.cooperativevoting.application.ports.out;

import com.cooperativevoting.domain.model.Voto;
import java.util.List;

public interface VotoRepositoryPort {
    Voto save(Voto voto);
    boolean existsBySessaoIdAndAssociadoId(String sessaoId, String associadoId);
    List<Voto> findBySessaoId(String sessaoId);
}
