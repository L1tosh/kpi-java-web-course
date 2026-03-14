package org.example.spacecatsmarket.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.example.spacecatsmarket.dto.category.CategoryEntry;
import org.example.spacecatsmarket.dto.validation.CosmicWordCheck;

@Value
@Builder
@Jacksonized
public class ProductDto {

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    @CosmicWordCheck
    String name;

    String description;

    @Min(value = 0, message = "Amount should be more than 0")
    @NotNull(message = "Name is mandatory")
    Double amount;

    @NotBlank(message = "Name is mandatory")
    String unit;

    @Min(value = 0, message = "Price should be more than 0")
    Double price;

    CategoryEntry category;
}
