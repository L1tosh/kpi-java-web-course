package org.example.spacecatsmarket.dto.cat;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CosmoCatDto {
    Long id;
    String name;
    String color;
    String breed;
}
