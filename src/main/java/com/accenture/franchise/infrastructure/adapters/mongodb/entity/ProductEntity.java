package com.accenture.franchise.infrastructure.adapters.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "products")
public class ProductEntity {
    @Id private String id;
    private String name;
    private Integer stock;
    private String branchId;
}