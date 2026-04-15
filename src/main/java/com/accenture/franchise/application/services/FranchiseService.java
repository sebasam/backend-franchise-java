package com.accenture.franchise.application.services;

import com.accenture.franchise.domain.model.*;
import com.accenture.franchise.domain.ports.FranchiseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FranchiseService {

    private final FranchiseRepositoryPort repositoryPort;

    public Mono<Franchise> createFranchise(Franchise franchise) {
        return repositoryPort.save(franchise);
    }

    public Mono<Franchise> updateFranchiseName(String id, String name) {
        return repositoryPort.updateName(id, name);
    }

    public Mono<Branch> createBranch(Branch branch) {
        return repositoryPort.saveBranch(branch);
    }

    public Mono<Branch> updateBranchName(String id, String name) {
        return repositoryPort.updateBranchName(id, name);
    }

    public Mono<Product> createProduct(Product product) {
        return repositoryPort.saveProduct(product);
    }

    public Mono<Void> deleteProduct(String branchId, String productId) {
        return repositoryPort.deleteProduct(branchId, productId);
    }

    public Mono<Product> updateProductStock(String productId, Integer stock) {
        return repositoryPort.updateProductStock(productId, stock);
    }

    public Mono<Product> updateProductName(String productId, String name) {
        return repositoryPort.updateProductName(productId, name);
    }

    public Flux<Product> getMaxStockProducts(String franchiseId) {
        return repositoryPort.findMaxStockProductsByFranchise(franchiseId);
    }
}