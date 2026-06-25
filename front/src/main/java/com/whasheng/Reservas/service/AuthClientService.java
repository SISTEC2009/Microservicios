package com.whasheng.Reservas.service;

import com.whasheng.Reservas.dto.LoginRequest;
import com.whasheng.Reservas.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthClientService {

    @Value("${app.ms-auth-url:http://localhost:8081}")
    private String msAuthUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public LoginResponse login(String username, String password) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<LoginRequest> request =
                    new HttpEntity<>(new LoginRequest(username, password), headers);

            ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                    msAuthUrl + "/auth/login",
                    request,
                    LoginResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            }

            return errorResponse("No se pudo validar las credenciales");

        } catch (RestClientException e) {
            return errorResponse("No se pudo conectar con el servicio de autenticación");
        }
    }

    private LoginResponse errorResponse(String mensaje) {
        LoginResponse response = new LoginResponse();
        response.setExito(false);
        response.setMensaje(mensaje);
        return response;
    }
}