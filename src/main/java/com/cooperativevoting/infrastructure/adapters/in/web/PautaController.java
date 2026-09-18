package com.cooperativevoting.infrastructure.adapters.in.web;

import com.cooperativevoting.application.ports.in.CriarPautaUseCase;
import com.cooperativevoting.domain.model.Pauta;
import com.cooperativevoting.infrastructure.adapters.in.web.dto.PautaRequest;
import com.cooperativevoting.infrastructure.adapters.in.web.dto.PautaResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/pautas")
@Tag(name = "Pautas", description = "Gerenciamento de pautas da assembleia")
public class PautaController {

    private final CriarPautaUseCase criarPautaUseCase;

    public PautaController(CriarPautaUseCase criarPautaUseCase) {
        this.criarPautaUseCase = criarPautaUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova pauta", description = "Cria uma pauta para ser votada posteriormente.")
    @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    public ResponseEntity<PautaResponse> criar(@RequestBody @Valid PautaRequest request) {
        Pauta pauta = criarPautaUseCase.criarPauta(request.getNome(), request.getDescricao());
        return ResponseEntity.status(HttpStatus.CREATED).body(new PautaResponse(pauta));
    }
}
