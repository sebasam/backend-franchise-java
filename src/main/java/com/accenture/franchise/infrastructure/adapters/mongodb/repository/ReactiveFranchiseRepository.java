package com.accenture.franchise.infrastructure.adapters.mongodb.repository;

import com.accenture.franchise.infrastructure.adapters.mongodb.entity.FranchiseEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReactiveFranchiseRepository extends ReactiveMongoRepository<FranchiseEntity, String> {}