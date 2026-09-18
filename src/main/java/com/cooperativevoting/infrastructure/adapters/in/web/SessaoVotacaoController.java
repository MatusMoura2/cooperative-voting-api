package com.cooperativevoting.infrastructure.adapters.in.web;

import com.cooperativevoting.application.ports.in.AbrirSessaoVotacaoUseCase;
import com.cooperativevoting.domain.model.SessaoVotacao;
import com.cooperativevoting.infrastructure.adapters.in.web.dto.SessaoRequest;
import com.cooperativevoting.infrastructure.adapters.in.web.dto.SessaoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/sessoes")
@Tag(name = "Sessões de Votação", description = "Abertura e controle do tempo das sessões de votação")
public class SessaoVotacaoController {

    private final AbrirSessaoVotacaoUseCase abrirSessaoUseCase;

    public SessaoVotacaoController(AbrirSessaoVotacaoUseCase abrirSessaoUseCase) {
        this.abrirSessaoUseCase = abrirSessaoUseCase;
    }

    @PostMapping
    @Operation(summary = "Abrir uma sessão de votação", description = "Abre uma sessão de votação para uma pauta. Se a duração não for enviada, assume 1 minuto por padrão.")
    @ApiResponse(responseCode = "201", description = "Sessão aberta com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "404", description = "Pauta não encontrada")
    public ResponseEntity<SessaoResponse> abrirSessao(@RequestBody @Valid SessaoRequest request) {
        SessaoVotacao sessao = abrirSessaoUseCase.abrirSessao(request.getPautaId(), request.getDuracaoMinutos());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SessaoResponse(sessao));
    }
}
