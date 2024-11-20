package org.example.spacecatsmarket.dto.cat;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CosmoCatListDto {
    List<CosmoCatDto> cosmoCatDtos;
}
