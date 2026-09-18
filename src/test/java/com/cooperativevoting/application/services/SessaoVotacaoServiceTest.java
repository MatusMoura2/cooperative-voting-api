package com.cooperativevoting.application.services;

import com.cooperativevoting.application.ports.out.PautaRepositoryPort;
import com.cooperativevoting.application.ports.out.SessaoVotacaoRepositoryPort;
import com.cooperativevoting.domain.exception.NotFoundException;
import com.cooperativevoting.domain.model.Pauta;
import com.cooperativevoting.domain.model.SessaoVotacao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessaoVotacaoServiceTest {

    @Mock
    private SessaoVotacaoRepositoryPort sessaoRepository;

    @Mock
    private PautaRepositoryPort pautaRepository;

    @InjectMocks
    private SessaoVotacaoService sessaoService;

    @Test
    void shouldOpenSessionSuccessfullyWithDefaultTime() {
        // Arrange
        String pautaId = "pauta-1";
        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(new Pauta(pautaId, "Pauta", "Desc")));
        when(sessaoRepository.save(any(SessaoVotacao.class))).thenAnswer(invocation -> {
            SessaoVotacao s = invocation.getArgument(0);
            s.setId("sessao-1");
            return s;
        });

        // Act
        SessaoVotacao result = sessaoService.abrirSessao(pautaId, null);

        // Assert
        assertNotNull(result);
        assertEquals("sessao-1", result.getId());
        assertEquals(pautaId, result.getPautaId());
        assertNotNull(result.getDataInicio());
        assertNotNull(result.getDataFim());
        assertTrue(result.getDataFim().isAfter(result.getDataInicio()));
        verify(sessaoRepository, times(1)).save(any(SessaoVotacao.class));
    }

    @Test
    void shouldThrowExceptionWhenPautaNotFound() {
        // Arrange
        when(pautaRepository.findById("invalid")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> sessaoService.abrirSessao("invalid", 10));
        verify(sessaoRepository, never()).save(any());
    }
}
