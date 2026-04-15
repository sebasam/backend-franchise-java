package com.accenture.franchise.infrastructure.entrypoints;

import com.accenture.franchise.application.services.FranchiseService;
import com.accenture.franchise.domain.model.Franchise;
import com.accenture.franchise.infrastructure.entrypoints.dto.FranchiseRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = FranchiseController.class)
class FranchiseControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private FranchiseService franchiseService;

    @Test
    void shouldReturnCreatedFranchise() {
        FranchiseRequest requestBody = new FranchiseRequest("Global Tech");
        Franchise responseBody = new Franchise("1", "Global Tech");

        when(franchiseService.createFranchise(any(Franchise.class))).thenReturn(Mono.just(responseBody));

        webTestClient.post()
                .uri("/api/v1/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.name").isEqualTo("Global Tech");
    }

    @Test
    void shouldReturnInternalServerErrorWhenServiceFails() {
        FranchiseRequest requestBody = new FranchiseRequest("Error Tech");

        when(franchiseService.createFranchise(any(Franchise.class)))
                .thenReturn(Mono.error(new RuntimeException("Unexpected error")));

        webTestClient.post()
                .uri("/api/v1/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().is5xxServerError();
    }
}