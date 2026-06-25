package com.whasheng.Reservas.service;

import com.whasheng.Reservas.model.Categoria;
import com.whasheng.Reservas.util.JwtSessionHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoriaService {

    @Value("${app.gateway-url:http://localhost:8970}")
    private String gatewayUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    private String baseUrl() {
        return gatewayUrl + "/Pedidos/categorias";
    }

    private HttpHeaders headersConToken() {
        HttpHeaders headers = new HttpHeaders();
        String token = JwtSessionHolder.obtenerToken();
        if (token != null) {
            headers.setBearerAuth(token);
        }
        return headers;
    }

    public List<Categoria> listarTodas() {
        Categoria[] respuesta = restTemplate.getForObject(baseUrl(), Categoria[].class);
        return respuesta != null ? Arrays.asList(respuesta) : List.of();
    }

    public Categoria obtenerPorId(Long id) {
        try {
            return restTemplate.getForObject(baseUrl() + "/" + id, Categoria.class);
        } catch (Exception e) {
            return null;
        }
    }

    public Categoria guardar(Categoria categoria) {
        HttpEntity<Categoria> request = new HttpEntity<>(categoria, headersConToken());

        if (categoria.getId() == null) {
            ResponseEntity<Categoria> response =
                    restTemplate.postForEntity(baseUrl(), request, Categoria.class);
            return response.getBody();
        }

        ResponseEntity<Categoria> response = restTemplate.exchange(
                baseUrl() + "/" + categoria.getId(),
                HttpMethod.PUT,
                request,
                Categoria.class
        );
        return response.getBody();
    }

    public void eliminar(Long id) {
        HttpEntity<Void> request = new HttpEntity<>(headersConToken());
        restTemplate.exchange(baseUrl() + "/" + id, HttpMethod.DELETE, request, Void.class);
    }
}