package com.cooperativevoting.infrastructure.adapters.in.web;

import com.cooperativevoting.application.ports.in.AbrirSessaoVotacaoUseCase;
import com.cooperativevoting.domain.model.SessaoVotacao;
import com.cooperativevoting.infrastructure.adapters.in.web.dto.SessaoRequest;
import com.cooperativevoting.infrastructure.adapters.in.web.dto.SessaoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sessoes")
public class SessaoVotacaoController {

    private final AbrirSessaoVotacaoUseCase abrirSessaoUseCase;

    public SessaoVotacaoController(AbrirSessaoVotacaoUseCase abrirSessaoUseCase) {
        this.abrirSessaoUseCase = abrirSessaoUseCase;
    }

    @PostMapping
    public ResponseEntity<SessaoResponse> abrirSessao(@RequestBody @Valid SessaoRequest request) {
        SessaoVotacao sessao = abrirSessaoUseCase.abrirSessao(request.getPautaId(), request.getDuracaoMinutos());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SessaoResponse(sessao));
    }
}
