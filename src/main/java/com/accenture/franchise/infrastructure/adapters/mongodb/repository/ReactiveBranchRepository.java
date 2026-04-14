package com.accenture.franchise.infrastructure.adapters.mongodb.repository;

import com.accenture.franchise.infrastructure.adapters.mongodb.entity.BranchEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ReactiveBranchRepository extends ReactiveMongoRepository<BranchEntity, String> {
    Flux<BranchEntity> findByFranchiseId(String franchiseId);
}