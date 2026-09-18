package com.cooperativevoting.application.services;

import com.cooperativevoting.application.ports.out.PautaRepositoryPort;
import com.cooperativevoting.domain.model.Pauta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PautaServiceTest {

    @Mock
    private PautaRepositoryPort pautaRepositoryPort;

    @InjectMocks
    private PautaService pautaService;

    @Test
    void shouldCreatePautaSuccessfully() {
        // Arrange
        Pauta savedPauta = new Pauta("123", "Pauta 1", "Descricao da Pauta 1");
        when(pautaRepositoryPort.save(any(Pauta.class))).thenReturn(savedPauta);

        // Act
        Pauta result = pautaService.criarPauta("Pauta 1", "Descricao da Pauta 1");

        // Assert
        assertNotNull(result);
        assertEquals("123", result.getId());
        assertEquals("Pauta 1", result.getNome());
        verify(pautaRepositoryPort, times(1)).save(any(Pauta.class));
    }
}
