package com.cooperativevoting.application.services;

import com.cooperativevoting.application.ports.in.ContabilizarVotosUseCase;
import com.cooperativevoting.application.ports.in.RegistrarVotoUseCase;
import com.cooperativevoting.application.ports.out.CpfValidationPort;
import com.cooperativevoting.application.ports.out.SessaoVotacaoRepositoryPort;
import com.cooperativevoting.application.ports.out.VotoRepositoryPort;
import com.cooperativevoting.domain.exception.BusinessException;
import com.cooperativevoting.domain.exception.NotFoundException;
import com.cooperativevoting.domain.model.ResultadoVotacao;
import com.cooperativevoting.domain.model.SessaoVotacao;
import com.cooperativevoting.domain.model.Voto;
import com.cooperativevoting.domain.model.VotoValor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VotacaoService implements RegistrarVotoUseCase, ContabilizarVotosUseCase {

    private final VotoRepositoryPort votoRepository;
    private final SessaoVotacaoRepositoryPort sessaoRepository;
    private final CpfValidationPort cpfValidationPort;

    public VotacaoService(VotoRepositoryPort votoRepository, SessaoVotacaoRepositoryPort sessaoRepository, CpfValidationPort cpfValidationPort) {
        this.votoRepository = votoRepository;
        this.sessaoRepository = sessaoRepository;
        this.cpfValidationPort = cpfValidationPort;
    }

    @Override
    public Voto registrarVoto(String sessaoId, String cpfAssociado, VotoValor valor) {
        SessaoVotacao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new NotFoundException("Sessão não encontrada."));

        if (!sessao.isAberta(LocalDateTime.now())) {
            throw new BusinessException("Sessão de votação está encerrada.");
        }

        if (votoRepository.existsBySessaoIdAndAssociadoId(sessaoId, cpfAssociado)) {
            throw new BusinessException("Associado já votou nesta sessão.");
        }

        if (!cpfValidationPort.canVote(cpfAssociado)) {
            throw new BusinessException("CPF inválido ou inabilitado para votar.");
        }

        Voto voto = new Voto(null, sessaoId, cpfAssociado, valor);
        return votoRepository.save(voto);
    }

    @Override
    public ResultadoVotacao contabilizar(String sessaoId) {
        List<Voto> votos = votoRepository.findBySessaoId(sessaoId);
        
        long totalSim = votos.stream().filter(v -> v.getValor() == VotoValor.SIM).count();
        long totalNao = votos.stream().filter(v -> v.getValor() == VotoValor.NAO).count();

        return new ResultadoVotacao(sessaoId, totalSim, totalNao);
    }
}
