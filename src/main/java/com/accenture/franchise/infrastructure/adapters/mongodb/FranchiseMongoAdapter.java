package com.accenture.franchise.infrastructure.adapters.mongodb;

import com.accenture.franchise.domain.model.*;
import com.accenture.franchise.domain.ports.FranchiseRepositoryPort;
import com.accenture.franchise.infrastructure.adapters.mongodb.entity.*;
import com.accenture.franchise.infrastructure.adapters.mongodb.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranchiseMongoAdapter implements FranchiseRepositoryPort {

    private final ReactiveFranchiseRepository franchiseRepo;
    private final ReactiveBranchRepository branchRepo;
    private final ReactiveProductRepository productRepo;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        FranchiseEntity entity = new FranchiseEntity();
        entity.setName(franchise.name());
        return franchiseRepo.save(entity)
                .map(saved -> new Franchise(saved.getId(), saved.getName()));
    }

    @Override
    public Mono<Franchise> updateName(String id, String name) {
        return franchiseRepo.findById(id)
                .flatMap(entity -> {
                    entity.setName(name);
                    return franchiseRepo.save(entity);
                })
                .map(saved -> new Franchise(saved.getId(), saved.getName()));
    }

    @Override
    public Mono<Branch> saveBranch(Branch branch) {
        BranchEntity entity = new BranchEntity();
        entity.setName(branch.name());
        entity.setFranchiseId(branch.franchiseId());
        return branchRepo.save(entity)
                .map(saved -> new Branch(saved.getId(), saved.getName(), saved.getFranchiseId()));
    }

    @Override
    public Mono<Branch> updateBranchName(String id, String name) {
        return branchRepo.findById(id)
                .flatMap(entity -> {
                    entity.setName(name);
                    return branchRepo.save(entity);
                })
                .map(saved -> new Branch(saved.getId(), saved.getName(), saved.getFranchiseId()));
    }

    @Override
    public Mono<Product> saveProduct(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setName(product.name());
        entity.setStock(product.stock());
        entity.setBranchId(product.branchId());
        return productRepo.save(entity)
                .map(saved -> new Product(saved.getId(), saved.getName(), saved.getStock(), saved.getBranchId()));
    }

    @Override
    public Mono<Void> deleteProduct(String branchId, String productId) {
        return productRepo.deleteByIdAndBranchId(productId, branchId);
    }

    @Override
    public Mono<Product> updateProductStock(String productId, Integer stock) {
        return productRepo.findById(productId)
                .flatMap(entity -> {
                    entity.setStock(stock);
                    return productRepo.save(entity);
                })
                .map(saved -> new Product(saved.getId(), saved.getName(), saved.getStock(), saved.getBranchId()));
    }

    @Override
    public Mono<Product> updateProductName(String productId, String name) {
        return productRepo.findById(productId)
                .flatMap(entity -> {
                    entity.setName(name);
                    return productRepo.save(entity);
                })
                .map(saved -> new Product(saved.getId(), saved.getName(), saved.getStock(), saved.getBranchId()));
    }

    @Override
    public Flux<Product> findMaxStockProductsByFranchise(String franchiseId) {
        return branchRepo.findByFranchiseId(franchiseId)
                .flatMap(branch -> productRepo.findFirstByBranchIdOrderByStockDesc(branch.getId()))
                .map(saved -> new Product(saved.getId(), saved.getName(), saved.getStock(), saved.getBranchId()));
    }
}