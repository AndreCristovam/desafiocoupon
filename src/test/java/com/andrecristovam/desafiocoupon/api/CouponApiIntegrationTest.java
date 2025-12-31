package com.andrecristovam.desafiocoupon.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.andrecristovam.desafiocoupon.DesafiocouponApplication;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = DesafiocouponApplication.class)
@AutoConfigureMockMvc
public class CouponApiIntegrationTest {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void criarECapturarCupomComSucessoTest() throws Exception {

		var expiration = OffsetDateTime.now().plusDays(1).withNano(0).toString();
    	
    	String body = """
    		    {
    		      "code": "ABC-123",
    		      "description": "Cupom integração",
    		      "discountValue": 1.5,
    		      "expirationDate": "%s",
    		      "published": true
    		    }
    		""".formatted(expiration);

        var result = mockMvc.perform(
                post("/coupon")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.code").value("ABC123"))
            .andExpect(jsonPath("$.status").value("ACTIVE"))
            .andReturn();

        String id = objectMapper
                .readTree(result.getResponse().getContentAsString())
                .get("id").asText();

        mockMvc.perform(get("/coupon/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("ABC123"))
            .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void deletarCupomComSucessoTest() throws Exception {

        String body = """
            {
              "code": "ABC-124",
              "description": "Cupom para deletar",
              "discountValue": 2.0,
              "expirationDate": "2030-01-01T10:00:00Z",
              "published": false
            }
        """;

        var result = mockMvc.perform(
                post("/coupon")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
            )
            .andExpect(status().isCreated())
            .andReturn();

        String id = objectMapper
                .readTree(result.getResponse().getContentAsString())
                .get("id").asText();

        mockMvc.perform(delete("/coupon/{id}", id))
            .andExpect(status().isNoContent());
    }

    @Test
    void retornar404QuandoCupomNaoExistirTest() throws Exception {
        mockMvc.perform(get("/coupon/{id}", "nao-existe"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cupom não encontrado"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void retornar400QuandoCodigoInvalidoTest() throws Exception {

        String body = """
            {
              "code": "A1",
              "description": "Cupom inválido",
              "discountValue": 1.0,
              "expirationDate": "2030-01-01T10:00:00Z",
              "published": false
            }
        """;

        mockMvc.perform(post("/coupon")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Código do cupom deve conter 6 caracteres alfanuméricos"));
    }

    @Test
    void retornar400QuandoDataExpiracaoNoPassadoTest() throws Exception {

        String body = """
            {
              "code": "ABC-125",
              "description": "Cupom expirado",
              "discountValue": 1.0,
              "expirationDate": "2000-01-01T10:00:00Z",
              "published": false
            }
        """;

        mockMvc.perform(post("/coupon")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Data de expiração não pode estar no passado"));
    }

    @Test
    void impedirSegundaExclusaoDeCupomTest() throws Exception {

        String body = """
            {
              "code": "ABC-126",
              "description": "Cupom deletável",
              "discountValue": 2.0,
              "expirationDate": "2030-01-01T10:00:00Z",
              "published": false
            }
        """;

        var result = mockMvc.perform(
                post("/coupon")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
            )
            .andReturn();

        String id = objectMapper
                .readTree(result.getResponse().getContentAsString())
                .get("id").asText();

        mockMvc.perform(delete("/coupon/{id}", id))
            .andExpect(status().isNoContent());

        mockMvc.perform(delete("/coupon/{id}", id))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Cupom já está deletado"));
    }
    
    @Test
    void naoPermitirCriarCupomDuplicadoTest() throws Exception {

    	var expiration = OffsetDateTime.now().plusDays(1).withNano(0).toString();
    	
        String body = """
            {
              "code": "ABC-127",
              "description": "Cupom duplicado",
              "discountValue": 1.5,
              "expirationDate": "%s",
              "published": true
            }
        """.formatted(expiration);
        
        mockMvc.perform(post("/coupon")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isCreated());

        mockMvc.perform(post("/coupon")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Já existe um cupom com este código"))
            .andExpect(jsonPath("$.status").value(400));
    }
}
