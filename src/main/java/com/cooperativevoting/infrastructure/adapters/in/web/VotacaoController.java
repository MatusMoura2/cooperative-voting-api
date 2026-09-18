package com.cooperativevoting.infrastructure.adapters.in.web;

import com.cooperativevoting.application.ports.in.ContabilizarVotosUseCase;
import com.cooperativevoting.application.ports.in.RegistrarVotoUseCase;
import com.cooperativevoting.domain.model.ResultadoVotacao;
import com.cooperativevoting.domain.model.VotoValor;
import com.cooperativevoting.infrastructure.adapters.in.web.dto.ResultadoResponse;
import com.cooperativevoting.infrastructure.adapters.in.web.dto.VotoRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/sessoes/{sessaoId}")
public class VotacaoController {

    private final RegistrarVotoUseCase registrarVotoUseCase;
    private final ContabilizarVotosUseCase contabilizarVotosUseCase;

    public VotacaoController(RegistrarVotoUseCase registrarVotoUseCase, ContabilizarVotosUseCase contabilizarVotosUseCase) {
        this.registrarVotoUseCase = registrarVotoUseCase;
        this.contabilizarVotosUseCase = contabilizarVotosUseCase;
    }

    @PostMapping("/votos")
    public ResponseEntity<Map<String, String>> registrarVoto(@PathVariable String sessaoId, 
                                                             @RequestBody @Valid VotoRequest request) {
        VotoValor valorEnum;
        try {
            valorEnum = VotoValor.valueOf(request.getValor().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", "O valor do voto deve ser SIM ou NAO"));
        }

        registrarVotoUseCase.registrarVoto(sessaoId, request.getCpfAssociado(), valorEnum);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "Voto registrado com sucesso"));
    }

    @GetMapping("/resultados")
    public ResponseEntity<ResultadoResponse> obterResultados(@PathVariable String sessaoId) {
        ResultadoVotacao resultado = contabilizarVotosUseCase.contabilizar(sessaoId);
        return ResponseEntity.ok(new ResultadoResponse(resultado));
    }
}
