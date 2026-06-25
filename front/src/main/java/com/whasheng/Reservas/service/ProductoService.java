package com.whasheng.Reservas.service;

import com.whasheng.Reservas.model.Producto;
import com.whasheng.Reservas.util.JwtSessionHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductoService {

	@Value("${app.gateway-url:http://localhost:8970}")
	private String gatewayUrl;

	private final RestTemplate restTemplate = new RestTemplate();

	private String baseUrl() {
		return gatewayUrl + "/Pedidos/productos";
	}

	private HttpHeaders headersConToken() {
		HttpHeaders headers = new HttpHeaders();
		String token = JwtSessionHolder.obtenerToken();
		if (token != null) {
			headers.setBearerAuth(token);
		}
		return headers;
	}

	public List<Producto> obtenerTodos() {
		Producto[] respuesta = restTemplate.getForObject(baseUrl(), Producto[].class);
		return respuesta != null ? Arrays.asList(respuesta) : List.of();
	}

	public List<Producto> filtrar(String nombre, Long categoria, String estado) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl());

		if (nombre != null && !nombre.isBlank()) {
			builder.queryParam("nombre", nombre);
		}
		if (categoria != null) {
			builder.queryParam("categoria", categoria);
		}
		if (estado != null && !estado.isBlank()) {
			builder.queryParam("estado", estado);
		}

		Producto[] respuesta = restTemplate.getForObject(builder.toUriString(), Producto[].class);
		return respuesta != null ? Arrays.asList(respuesta) : List.of();
	}

	public Producto obtener(Long id) {
		return restTemplate.getForObject(baseUrl() + "/" + id, Producto.class);
	}

	public Producto guardar(Producto p, MultipartFile archivo) throws IOException {

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("nombre", p.getNombre());
		body.add("descripcion", p.getDescripcion());
		body.add("precio", p.getPrecio());
		body.add("estado", p.getEstado());

		if (p.getCategoria() != null && p.getCategoria().getId() != null) {
			body.add("categoria.id", p.getCategoria().getId());
		}

		if (archivo != null && !archivo.isEmpty()) {
			ByteArrayResource recurso = new ByteArrayResource(archivo.getBytes()) {
				@Override
				public String getFilename() {
					return archivo.getOriginalFilename();
				}
			};
			body.add("archivo", recurso);
		}

		HttpHeaders headers = headersConToken();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		if (p.getId() == null) {
			ResponseEntity<Producto> response =
					restTemplate.postForEntity(baseUrl(), request, Producto.class);
			return response.getBody();
		}

		ResponseEntity<Producto> response = restTemplate.exchange(
				baseUrl() + "/" + p.getId(),
				HttpMethod.PUT,
				request,
				Producto.class
		);
		return response.getBody();
	}

	public void eliminar(Long id) {
		HttpEntity<Void> request = new HttpEntity<>(headersConToken());
		restTemplate.exchange(baseUrl() + "/" + id, HttpMethod.DELETE, request, Void.class);
	}
}