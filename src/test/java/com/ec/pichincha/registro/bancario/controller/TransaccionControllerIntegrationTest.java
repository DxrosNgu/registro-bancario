package com.ec.pichincha.registro.bancario.controller;

import com.ec.pichincha.registro.bancario.model.dto.transaccion.TransaccionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TransaccionControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenValidRequest_whenRegistrarTransaccion_thenReturns200() throws JsonProcessingException {
        // Arrange: Create a test transaction request
        TransaccionRequest transaccionRequest = new TransaccionRequest();
        transaccionRequest.setNumeroCuenta("123456789");
        transaccionRequest.setTipoMovimiento("Deposito");
        transaccionRequest.setValor(500.0);

        String requestBody = objectMapper.writeValueAsString(transaccionRequest);

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/v1/registro-bancario/transaccion")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("saldoInicial", notNullValue())
                .body("movimiento", equalTo(500.0F))
                .body("tipoMovimiento", equalTo("Deposito"));
    }
}
