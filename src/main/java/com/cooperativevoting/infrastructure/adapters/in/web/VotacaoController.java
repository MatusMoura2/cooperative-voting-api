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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/sessoes/{sessaoId}")
@Tag(name = "Votação e Resultados", description = "Registro de votos dos associados e contabilização de resultados")
public class VotacaoController {

    private final RegistrarVotoUseCase registrarVotoUseCase;
    private final ContabilizarVotosUseCase contabilizarVotosUseCase;

    public VotacaoController(RegistrarVotoUseCase registrarVotoUseCase, ContabilizarVotosUseCase contabilizarVotosUseCase) {
        this.registrarVotoUseCase = registrarVotoUseCase;
        this.contabilizarVotosUseCase = contabilizarVotosUseCase;
    }

    @PostMapping("/votos")
    @Operation(summary = "Registrar um voto", description = "Registra o voto de um associado em uma sessão. O CPF será validado em um sistema externo.")
    @ApiResponse(responseCode = "201", description = "Voto registrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Voto inválido, CPF inabilitado, ou erro de negócio (ex: sessão fechada)")
    @ApiResponse(responseCode = "404", description = "Sessão não encontrada")
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
    @Operation(summary = "Contabilizar resultados", description = "Retorna o total de votos SIM/NAO e o status de aprovação da pauta.")
    @ApiResponse(responseCode = "200", description = "Resultados contabilizados com sucesso")
    @ApiResponse(responseCode = "404", description = "Sessão não encontrada")
    public ResponseEntity<ResultadoResponse> obterResultados(@PathVariable String sessaoId) {
        ResultadoVotacao resultado = contabilizarVotosUseCase.contabilizar(sessaoId);
        return ResponseEntity.ok(new ResultadoResponse(resultado));
    }
}
