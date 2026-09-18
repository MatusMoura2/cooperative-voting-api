package com.cooperativevoting.application.services;

import com.cooperativevoting.application.ports.in.AbrirSessaoVotacaoUseCase;
import com.cooperativevoting.application.ports.out.PautaRepositoryPort;
import com.cooperativevoting.application.ports.out.SessaoVotacaoRepositoryPort;
import com.cooperativevoting.domain.exception.NotFoundException;
import com.cooperativevoting.domain.model.SessaoVotacao;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessaoVotacaoService implements AbrirSessaoVotacaoUseCase {

    private final SessaoVotacaoRepositoryPort sessaoRepository;
    private final PautaRepositoryPort pautaRepository;
    private static final int DEFAULT_DURATION_MINUTES = 1;

    public SessaoVotacaoService(SessaoVotacaoRepositoryPort sessaoRepository, PautaRepositoryPort pautaRepository) {
        this.sessaoRepository = sessaoRepository;
        this.pautaRepository = pautaRepository;
    }

    @Override
    public SessaoVotacao abrirSessao(String pautaId, Integer duracaoMinutos) {
        pautaRepository.findById(pautaId)
                .orElseThrow(() -> new NotFoundException("Pauta não encontrada."));

        int duracao = (duracaoMinutos != null && duracaoMinutos > 0) ? duracaoMinutos : DEFAULT_DURATION_MINUTES;
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = inicio.plusMinutes(duracao);

        SessaoVotacao sessao = new SessaoVotacao(null, pautaId, inicio, fim);
        return sessaoRepository.save(sessao);
    }
}
