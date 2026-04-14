package com.accenture.franchise.infrastructure.adapters.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "branches")
public class BranchEntity {
    @Id private String id;
    private String name;
    private String franchiseId;
}