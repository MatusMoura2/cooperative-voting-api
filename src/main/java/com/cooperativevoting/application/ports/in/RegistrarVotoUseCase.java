package com.cooperativevoting.application.ports.in;

import com.cooperativevoting.domain.model.Voto;
import com.cooperativevoting.domain.model.VotoValor;

public interface RegistrarVotoUseCase {
    Voto registrarVoto(String sessaoId, String cpfAssociado, VotoValor valor);
}
