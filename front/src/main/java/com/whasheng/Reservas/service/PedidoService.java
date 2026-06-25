package com.whasheng.Reservas.service;

import com.whasheng.Reservas.model.Pedido;
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
public class PedidoService {

	@Value("${app.gateway-url:http://localhost:8970}")
	private String gatewayUrl;

	private final RestTemplate restTemplate = new RestTemplate();

	private String baseUrl() {
		return gatewayUrl + "/Pedidos/pedidos";
	}

	private HttpHeaders headersConToken() {
		HttpHeaders headers = new HttpHeaders();
		String token = JwtSessionHolder.obtenerToken();
		if (token != null) {
			headers.setBearerAuth(token);
		}
		return headers;
	}

	public List<Pedido> findAll() {
		Pedido[] respuesta = restTemplate.getForObject(baseUrl(), Pedido[].class);
		return respuesta != null ? Arrays.asList(respuesta) : List.of();
	}

	public Pedido obtener(Long id) {
		try {
			return restTemplate.getForObject(baseUrl() + "/" + id, Pedido.class);
		} catch (Exception e) {
			return null;
		}
	}

	public void eliminar(Long id) {
		HttpEntity<Void> request = new HttpEntity<>(headersConToken());
		restTemplate.exchange(baseUrl() + "/" + id, HttpMethod.DELETE, request, Void.class);
	}

	public Pedido guardarPedido(Pedido pedido) {
		HttpEntity<Pedido> request = new HttpEntity<>(pedido, headersConToken());

		if (pedido.getIdPedido() == null) {
			ResponseEntity<Pedido> response =
					restTemplate.postForEntity(baseUrl(), request, Pedido.class);
			return response.getBody();
		}

		ResponseEntity<Pedido> response = restTemplate.exchange(
				baseUrl() + "/" + pedido.getIdPedido(),
				HttpMethod.PUT,
				request,
				Pedido.class
		);
		return response.getBody();
	}

	public Pedido rechazar(Long id) {
		HttpEntity<Void> request = new HttpEntity<>(headersConToken());
		ResponseEntity<Pedido> response = restTemplate.exchange(
				baseUrl() + "/" + id + "/rechazar",
				HttpMethod.POST,
				request,
				Pedido.class
		);
		return response.getBody();
	}
}