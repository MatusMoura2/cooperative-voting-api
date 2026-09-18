package com.cooperativevoting.infrastructure.adapters.in.web;

import com.cooperativevoting.application.ports.in.CriarPautaUseCase;
import com.cooperativevoting.domain.model.Pauta;
import com.cooperativevoting.infrastructure.adapters.in.web.dto.PautaRequest;
import com.cooperativevoting.infrastructure.adapters.in.web.dto.PautaResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pautas")
public class PautaController {

    private final CriarPautaUseCase criarPautaUseCase;

    public PautaController(CriarPautaUseCase criarPautaUseCase) {
        this.criarPautaUseCase = criarPautaUseCase;
    }

    @PostMapping
    public ResponseEntity<PautaResponse> criar(@RequestBody @Valid PautaRequest request) {
        Pauta pauta = criarPautaUseCase.criarPauta(request.getNome(), request.getDescricao());
        return ResponseEntity.status(HttpStatus.CREATED).body(new PautaResponse(pauta));
    }
}
