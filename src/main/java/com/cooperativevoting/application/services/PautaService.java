package com.cooperativevoting.application.services;

import com.cooperativevoting.application.ports.in.CriarPautaUseCase;
import com.cooperativevoting.application.ports.out.PautaRepositoryPort;
import com.cooperativevoting.domain.model.Pauta;
import org.springframework.stereotype.Service;

@Service
public class PautaService implements CriarPautaUseCase {

    private final PautaRepositoryPort pautaRepositoryPort;

    public PautaService(PautaRepositoryPort pautaRepositoryPort) {
        this.pautaRepositoryPort = pautaRepositoryPort;
    }

    @Override
    public Pauta criarPauta(String nome, String descricao) {
        Pauta pauta = new Pauta(null, nome, descricao);
        return pautaRepositoryPort.save(pauta);
    }
}
