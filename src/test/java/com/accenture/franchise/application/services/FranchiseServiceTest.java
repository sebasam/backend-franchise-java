package com.accenture.franchise.application.services;

import com.accenture.franchise.domain.model.Franchise;
import com.accenture.franchise.domain.model.Product;
import com.accenture.franchise.domain.ports.FranchiseRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranchiseServiceTest {

    @Mock
    private FranchiseRepositoryPort repositoryPort;

    @InjectMocks
    private FranchiseService service;

    @Test
    void shouldCreateFranchiseSuccessfully() {
        Franchise newFranchise = new Franchise(null, "Accenture Tech");
        Franchise savedFranchise = new Franchise("123", "Accenture Tech");

        when(repositoryPort.save(any(Franchise.class))).thenReturn(Mono.just(savedFranchise));

        Mono<Franchise> result = service.createFranchise(newFranchise);

        StepVerifier.create(result)
                .expectNextMatches(f -> f.id().equals("123") && f.name().equals("Accenture Tech"))
                .verifyComplete();
    }

    @Test
    void shouldGetMaxStockProductsSuccessfully() {
        Product p1 = new Product("1", "Camisa", 50, "branch1");
        Product p2 = new Product("2", "Pantalon", 30, "branch2");

        when(repositoryPort.findMaxStockProductsByFranchise("franchise1"))
                .thenReturn(Flux.just(p1, p2));

        Flux<Product> result = service.getMaxStockProducts("franchise1");

        StepVerifier.create(result)
                .expectNextMatches(p -> p.name().equals("Camisa") && p.stock() == 50)
                .expectNextMatches(p -> p.name().equals("Pantalon") && p.stock() == 30)
                .verifyComplete();
    }

    @Test
    void shouldHandleEmptyMaxStockProducts() {
        when(repositoryPort.findMaxStockProductsByFranchise("emptyFranchise"))
                .thenReturn(Flux.empty());

        Flux<Product> result = service.getMaxStockProducts("emptyFranchise");

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void shouldHandleDatabaseErrorOnCreateBranch() {
        when(repositoryPort.saveBranch(any())).thenReturn(Mono.error(new RuntimeException("DB Connection Failed")));

        Mono<com.accenture.franchise.domain.model.Branch> result = service.createBranch(
                new com.accenture.franchise.domain.model.Branch(null, "Fail Branch", "1")
        );

        StepVerifier.create(result)
                .expectErrorMessage("DB Connection Failed")
                .verify();
    }
}