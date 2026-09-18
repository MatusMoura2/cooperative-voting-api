package com.cooperativevoting.infrastructure.adapters.in.web;

import com.cooperativevoting.domain.model.Pauta;
import com.cooperativevoting.domain.model.SessaoVotacao;
import com.cooperativevoting.infrastructure.adapters.in.web.dto.VotoRequest;
import com.cooperativevoting.infrastructure.adapters.out.persistence.entity.PautaEntity;
import com.cooperativevoting.infrastructure.adapters.out.persistence.entity.SessaoVotacaoEntity;
import com.cooperativevoting.infrastructure.adapters.out.persistence.repository.SpringDataPautaRepository;
import com.cooperativevoting.infrastructure.adapters.out.persistence.repository.SpringDataSessaoVotacaoRepository;
import com.cooperativevoting.infrastructure.adapters.out.persistence.repository.SpringDataVotoRepository;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest(httpPort = 8089)
class VotacaoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private SpringDataPautaRepository pautaRepository;

    @Autowired
    private SpringDataSessaoVotacaoRepository sessaoRepository;

    @Autowired
    private SpringDataVotoRepository votoRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("external.api.cpf.url", () -> "http://localhost:8089/users/{cpf}");
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        votoRepository.deleteAll();
        sessaoRepository.deleteAll();
        pautaRepository.deleteAll();
    }

    @Test
    void shouldRegisterVoteSuccessfullyWhenCpfIsAbleToVote() {
        PautaEntity pauta = pautaRepository.save(new PautaEntity(null, "Pauta Teste", "Descricao"));
        SessaoVotacaoEntity sessao = sessaoRepository.save(new SessaoVotacaoEntity(
                null, pauta.getId(), LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(10)
        ));

        // Mock external API
        stubFor(get(urlEqualTo("/users/12345678901"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"status\": \"ABLE_TO_VOTE\"}")));

        String requestJson = "{\n" +
                "  \"cpfAssociado\": \"12345678901\",\n" +
                "  \"valor\": \"SIM\"\n" +
                "}";

        given()
            .contentType(ContentType.JSON)
            .body(requestJson)
        .when()
            .post("/api/v1/sessoes/" + sessao.getId() + "/votos")
        .then()
            .statusCode(201)
            .body("mensagem", equalTo("Voto registrado com sucesso"));
    }

    @Test
    void shouldReturn400WhenCpfIsUnableToVote() {
        PautaEntity pauta = pautaRepository.save(new PautaEntity(null, "Pauta 2", "Descricao"));
        SessaoVotacaoEntity sessao = sessaoRepository.save(new SessaoVotacaoEntity(
                null, pauta.getId(), LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(10)
        ));

        // Mock external API to return UNABLE_TO_VOTE
        stubFor(get(urlEqualTo("/users/98765432100"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"status\": \"UNABLE_TO_VOTE\"}")));

        String requestJson = "{\n" +
                "  \"cpfAssociado\": \"98765432100\",\n" +
                "  \"valor\": \"SIM\"\n" +
                "}";

        given()
            .contentType(ContentType.JSON)
            .body(requestJson)
        .when()
            .post("/api/v1/sessoes/" + sessao.getId() + "/votos")
        .then()
            .statusCode(400)
            .body("erro", equalTo("CPF inválido ou inabilitado para votar."));
    }
}
