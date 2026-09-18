package com.cooperativevoting.application.ports.in;

import com.cooperativevoting.domain.model.Pauta;

public interface CriarPautaUseCase {
    Pauta criarPauta(String nome, String descricao);
}
