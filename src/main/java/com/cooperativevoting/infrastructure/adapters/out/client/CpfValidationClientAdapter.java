package com.cooperativevoting.infrastructure.adapters.out.client;

import com.cooperativevoting.application.ports.out.CpfValidationPort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Component
public class CpfValidationClientAdapter implements CpfValidationPort {

    private final RestTemplate restTemplate;
    private static final String URL = "https://user-info.herokuapp.com/users/{cpf}";

    public CpfValidationClientAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean canVote(String cpf) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> response = restTemplate.getForObject(URL, Map.class, cpf);
            return response != null && "ABLE_TO_VOTE".equals(response.get("status"));
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;
            }
            // If the heroku API is down (503) or other errors, we might want to default to true for testing
            // or rethrow. Let's return false as a safe fallback, or log and return true for the sake of the challenge.
            // But strict business rule says we should know. 
            // For now, let's assume if it's not 200 ABLE_TO_VOTE, they can't vote.
            return false;
        } catch (Exception e) {
            // Fallback for Heroku being offline entirely (connection refused, etc)
            System.err.println("Erro ao comunicar com a API do Heroku: " + e.getMessage());
            // Para não travar os testes caso a API original esteja de fato fora do ar (comum hoje em dia),
            // podemos retornar true ou false. Retornarei false, mas o ideal seria um Circuit Breaker.
            return false;
        }
    }
}
