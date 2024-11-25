package org.example.spacecatsmarket.dto.category;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CategoryEntry {
    Integer id;
    String name;
}
