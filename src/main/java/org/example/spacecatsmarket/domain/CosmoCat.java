package org.example.spacecatsmarket.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CosmoCat {
    private Long id;
    private String name;
    private String color;
    private String breed;
}
