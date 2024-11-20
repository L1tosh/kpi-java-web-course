package org.example.spacecatsmarket.web;

import lombok.RequiredArgsConstructor;
import org.example.spacecatsmarket.dto.cat.CosmoCatListDto;
import org.example.spacecatsmarket.service.CosmoCatService;
import org.example.spacecatsmarket.service.mapper.CosmoCatMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cosmo-cats")
public class CosmoCatController {

    private final CosmoCatService cosmoCatService;
    private final CosmoCatMapper mapper;

    @GetMapping
    public ResponseEntity<CosmoCatListDto> getAllCosmoCats() {
        return ResponseEntity.ok(mapper.toCosmoCatListDto(cosmoCatService.getCosmoCats()));
    }
}
