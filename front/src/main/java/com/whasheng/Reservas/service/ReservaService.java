package com.whasheng.Reservas.service;

import com.whasheng.Reservas.model.Reserva;
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
public class ReservaService {

	@Value("${app.gateway-url:http://localhost:8970}")
	private String gatewayUrl;

	private final RestTemplate restTemplate = new RestTemplate();

	private String baseUrl() {
		return gatewayUrl + "/Reservas/api/reservas";
	}

	private HttpHeaders headersConToken() {
		HttpHeaders headers = new HttpHeaders();
		String token = JwtSessionHolder.obtenerToken();
		if (token != null) {
			headers.setBearerAuth(token);
		}
		return headers;
	}

	public List<Reserva> ListaTodo() {
		HttpEntity<Void> request = new HttpEntity<>(headersConToken());
		ResponseEntity<Reserva[]> response = restTemplate.exchange(
				baseUrl(), HttpMethod.GET, request, Reserva[].class);
		Reserva[] respuesta = response.getBody();
		return respuesta != null ? Arrays.asList(respuesta) : List.of();
	}

	public Reserva buscarPorId(Long id) {
		try {
			HttpEntity<Void> request = new HttpEntity<>(headersConToken());
			ResponseEntity<Reserva> response = restTemplate.exchange(
					baseUrl() + "/" + id, HttpMethod.GET, request, Reserva.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public Reserva save(Reserva reserva) {
		HttpEntity<Reserva> request = new HttpEntity<>(reserva, headersConToken());

		if (reserva.getIdReserva() == null) {
			ResponseEntity<Reserva> response =
					restTemplate.postForEntity(baseUrl(), request, Reserva.class);
			return response.getBody();
		}

		ResponseEntity<Reserva> response = restTemplate.exchange(
				baseUrl() + "/" + reserva.getIdReserva(),
				HttpMethod.PUT,
				request,
				Reserva.class
		);
		return response.getBody();
	}

	public void eliminar(Long id) {
		HttpEntity<Void> request = new HttpEntity<>(headersConToken());
		restTemplate.exchange(baseUrl() + "/" + id, HttpMethod.DELETE, request, Void.class);
	}
}