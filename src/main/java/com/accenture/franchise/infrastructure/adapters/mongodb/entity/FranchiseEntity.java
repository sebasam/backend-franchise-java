package com.accenture.franchise.infrastructure.adapters.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "franchises")
public class FranchiseEntity {
    @Id private String id;
    private String name;
}