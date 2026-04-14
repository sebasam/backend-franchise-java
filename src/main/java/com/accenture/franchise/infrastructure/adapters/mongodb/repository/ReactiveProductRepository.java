package com.accenture.franchise.infrastructure.adapters.mongodb.repository;

import com.accenture.franchise.infrastructure.adapters.mongodb.entity.ProductEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ReactiveProductRepository extends ReactiveMongoRepository<ProductEntity, String> {
    Mono<Void> deleteByIdAndBranchId(String id, String branchId);
    Mono<ProductEntity> findFirstByBranchIdOrderByStockDesc(String branchId);
}