package com.cooperativevoting.application.ports.in;

import com.cooperativevoting.domain.model.ResultadoVotacao;

public interface ContabilizarVotosUseCase {
    ResultadoVotacao contabilizar(String pautaId);
}
