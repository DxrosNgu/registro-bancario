package com.ec.pichincha.registro.bancario.util.client;

import com.ec.pichincha.registro.bancario.model.client.ClienteResponse;
import com.ec.pichincha.registro.bancario.util.exception.UsuarioException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class ClienteClient {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ClienteResponse getClienteById(String clienteId) throws UsuarioException {
        String clienteServiceUrl = "http://localhost:8080/v1/perfil-usuario/clientes/" + clienteId;

        Request request = new Request.Builder()
                .url(clienteServiceUrl)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return objectMapper.readValue(response.body().string(), ClienteResponse.class);
            } else {
                throw new UsuarioException(response.message());
            }
        } catch (IOException | UsuarioException e) {
            throw new UsuarioException("Failed to fetch client data: " + e.getMessage());
        }
    }
}
