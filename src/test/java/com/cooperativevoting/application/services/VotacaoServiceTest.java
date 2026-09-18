package com.cooperativevoting.application.services;

import com.cooperativevoting.application.ports.out.CpfValidationPort;
import com.cooperativevoting.application.ports.out.SessaoVotacaoRepositoryPort;
import com.cooperativevoting.application.ports.out.VotoRepositoryPort;
import com.cooperativevoting.domain.exception.BusinessException;
import com.cooperativevoting.domain.exception.NotFoundException;
import com.cooperativevoting.domain.model.ResultadoVotacao;
import com.cooperativevoting.domain.model.SessaoVotacao;
import com.cooperativevoting.domain.model.Voto;
import com.cooperativevoting.domain.model.VotoValor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VotacaoServiceTest {

    @Mock
    private VotoRepositoryPort votoRepository;

    @Mock
    private SessaoVotacaoRepositoryPort sessaoRepository;

    @Mock
    private CpfValidationPort cpfValidationPort;

    @InjectMocks
    private VotacaoService votacaoService;

    @Test
    void shouldRegisterVoteSuccessfully() {
        // Arrange
        String sessaoId = "sessao-1";
        String cpf = "12345678901";
        SessaoVotacao sessao = new SessaoVotacao(sessaoId, "pauta-1", LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(5));
        
        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessao));
        when(cpfValidationPort.canVote(cpf)).thenReturn(true);
        when(votoRepository.existsBySessaoIdAndAssociadoId(sessaoId, cpf)).thenReturn(false);
        when(votoRepository.save(any(Voto.class))).thenAnswer(i -> {
            Voto v = i.getArgument(0);
            v.setId("voto-1");
            return v;
        });

        // Act
        Voto result = votacaoService.registrarVoto(sessaoId, cpf, VotoValor.SIM);

        // Assert
        assertNotNull(result);
        assertEquals("voto-1", result.getId());
        assertEquals(VotoValor.SIM, result.getValor());
        verify(votoRepository).save(any(Voto.class));
    }

    @Test
    void shouldFailIfSessionClosed() {
        String sessaoId = "sessao-1";
        SessaoVotacao sessao = new SessaoVotacao(sessaoId, "pauta-1", LocalDateTime.now().minusMinutes(10), LocalDateTime.now().minusMinutes(5));
        
        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessao));

        assertThrows(BusinessException.class, () -> votacaoService.registrarVoto(sessaoId, "123", VotoValor.SIM));
    }

    @Test
    void shouldFailIfSessionNotFound() {
        String sessaoId = "sessao-invalid";
        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> votacaoService.registrarVoto(sessaoId, "123", VotoValor.SIM));
    }

    @Test
    void shouldFailIfAlreadyVoted() {
        String sessaoId = "sessao-1";
        String cpf = "123";
        SessaoVotacao sessao = new SessaoVotacao(sessaoId, "pauta-1", LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(5));
        
        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessao));
        when(votoRepository.existsBySessaoIdAndAssociadoId(sessaoId, cpf)).thenReturn(true);

        assertThrows(BusinessException.class, () -> votacaoService.registrarVoto(sessaoId, cpf, VotoValor.SIM));
    }

    @Test
    void shouldFailIfCpfInvalid() {
        String sessaoId = "sessao-1";
        String cpf = "123";
        SessaoVotacao sessao = new SessaoVotacao(sessaoId, "pauta-1", LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(5));
        
        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessao));
        when(votoRepository.existsBySessaoIdAndAssociadoId(sessaoId, cpf)).thenReturn(false);
        when(cpfValidationPort.canVote(cpf)).thenReturn(false);

        assertThrows(BusinessException.class, () -> votacaoService.registrarVoto(sessaoId, cpf, VotoValor.SIM));
    }

    @Test
    void shouldCountVotesSuccessfully() {
        // Arrange
        String pautaId = "pauta-1";
        String sessaoId = "sessao-1"; // Assuming pautaId maps to sessao for counting
        
        // Let's assume the use case takes sessaoId for now, or pautaId and fetches sessao.
        // I will implement it fetching sessao by pautaId (requires a method) or just taking sessaoId.
        // Actually, let's just make it take sessaoId for simplicity.
        
        Voto v1 = new Voto("1", sessaoId, "cpf1", VotoValor.SIM);
        Voto v2 = new Voto("2", sessaoId, "cpf2", VotoValor.SIM);
        Voto v3 = new Voto("3", sessaoId, "cpf3", VotoValor.NAO);
        
        when(votoRepository.findBySessaoId(sessaoId)).thenReturn(List.of(v1, v2, v3));
        
        // Act
        ResultadoVotacao result = votacaoService.contabilizar(sessaoId);
        
        // Assert
        assertEquals(2, result.getTotalSim());
        assertEquals(1, result.getTotalNao());
        assertEquals("APROVADA", result.getStatusAprovacao());
    }
}
