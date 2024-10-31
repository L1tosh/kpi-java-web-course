package org.example.spacecatsmarket.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ProductDto {
    String name;
    String description;
    Double amount;
    String unit;
    Double price;
    CategoryEntryListDto categories;
}
