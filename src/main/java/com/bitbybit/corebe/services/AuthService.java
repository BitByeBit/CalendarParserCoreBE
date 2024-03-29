package com.bitbybit.corebe.services;

import io.prometheus.client.Counter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class AuthService {
    @Value("${authenticator.url}")
    private String authenticatorUrl;

    public boolean isInvalidUser(String userUid, String token) {
        return !userUid.equals(validateToken(token.split(" ")[1].trim()));
    }

    public String validateToken(String token) {
        WebClient webClient = WebClient.create(authenticatorUrl);
        try {
            return webClient.post()
                    .uri("/validate")
                    .contentType(MediaType.TEXT_PLAIN)
                    .bodyValue(token)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
