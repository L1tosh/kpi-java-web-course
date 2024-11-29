package org.example.spacecatsmarket.domain;

import lombok.Builder;
import lombok.Data;
import org.example.spacecatsmarket.common.Unit;

@Data
@Builder
public class Product {

    Long id;
    String name;
    String description;
    Double price;
    Double amount;
    Unit unit;
    Category category;
}
