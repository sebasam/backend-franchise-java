package com.accenture.franchise.infrastructure.entrypoints;

import com.accenture.franchise.application.services.FranchiseService;
import com.accenture.franchise.domain.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/franchises")
@RequiredArgsConstructor
@Tag(name = "Franchise API", description = "Endpoints reactivos para franquicias, sucursales y productos")
public class FranchiseController {

    private final FranchiseService service;

    @PostMapping
    @Operation(summary = "Agregar una nueva franquicia")
    public Mono<Franchise> createFranchise(@RequestBody Franchise franchise) {
        return service.createFranchise(franchise);
    }

    @PatchMapping("/{id}/name")
    @Operation(summary = "Actualizar nombre de la franquicia")
    public Mono<Franchise> updateFranchiseName(@PathVariable String id, @RequestParam String name) {
        return service.updateFranchiseName(id, name);
    }

    @PostMapping("/{franchiseId}/branches")
    @Operation(summary = "Agregar una nueva sucursal a la franquicia")
    public Mono<Branch> createBranch(@PathVariable String franchiseId, @RequestBody Branch branch) {
        return service.createBranch(new Branch(null, branch.name(), franchiseId));
    }

    @PatchMapping("/branches/{branchId}/name")
    @Operation(summary = "Actualizar nombre de la sucursal")
    public Mono<Branch> updateBranchName(@PathVariable String branchId, @RequestParam String name) {
        return service.updateBranchName(branchId, name);
    }

    @PostMapping("/branches/{branchId}/products")
    @Operation(summary = "Agregar un nuevo producto a la sucursal")
    public Mono<Product> createProduct(@PathVariable String branchId, @RequestBody Product product) {
        return service.createProduct(new Product(null, product.name(), product.stock(), branchId));
    }

    @DeleteMapping("/branches/{branchId}/products/{productId}")
    @Operation(summary = "Eliminar un producto de una sucursal")
    public Mono<Void> deleteProduct(@PathVariable String branchId, @PathVariable String productId) {
        return service.deleteProduct(branchId, productId);
    }

    @PatchMapping("/products/{productId}/stock")
    @Operation(summary = "Modificar el stock de un producto")
    public Mono<Product> updateProductStock(@PathVariable String productId, @RequestParam Integer stock) {
        return service.updateProductStock(productId, stock);
    }

    @PatchMapping("/products/{productId}/name")
    @Operation(summary = "Actualizar nombre del producto")
    public Mono<Product> updateProductName(@PathVariable String productId, @RequestParam String name) {
        return service.updateProductName(productId, name);
    }

    @GetMapping("/{franchiseId}/max-stock-products")
    @Operation(summary = "Producto con más stock por sucursal para una franquicia puntual")
    public Flux<Product> getMaxStockProducts(@PathVariable String franchiseId) {
        return service.getMaxStockProducts(franchiseId);
    }
}