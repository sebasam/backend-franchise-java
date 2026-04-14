package com.accenture.franchise.domain.ports;

import com.accenture.franchise.domain.model.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseRepositoryPort {
    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> updateName(String id, String name);
    Mono<Branch> saveBranch(Branch branch);
    Mono<Branch> updateBranchName(String id, String name);
    Mono<Product> saveProduct(Product product);
    Mono<Void> deleteProduct(String branchId, String productId);
    Mono<Product> updateProductStock(String productId, Integer stock);
    Mono<Product> updateProductName(String productId, String name);
    Flux<Product> findMaxStockProductsByFranchise(String franchiseId);
}