package org.example.spacecatsmarket.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CategoryDto {

    @NotBlank(message = "Name is mandatory")
    @Size(max = 50, message = "Name cannot exceed 100 characters")
    String name;
}
