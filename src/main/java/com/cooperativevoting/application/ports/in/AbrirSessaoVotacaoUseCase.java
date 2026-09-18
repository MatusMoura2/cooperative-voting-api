package com.cooperativevoting.application.ports.in;

import com.cooperativevoting.domain.model.SessaoVotacao;

public interface AbrirSessaoVotacaoUseCase {
    SessaoVotacao abrirSessao(String pautaId, Integer duracaoMinutos);
}
