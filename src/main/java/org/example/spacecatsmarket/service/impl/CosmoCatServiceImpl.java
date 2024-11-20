package org.example.spacecatsmarket.service.impl;

import org.example.spacecatsmarket.domain.CosmoCat;
import org.example.spacecatsmarket.featuretoggle.annotation.FeatureToggle;
import org.example.spacecatsmarket.service.CosmoCatService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.spacecatsmarket.featuretoggle.FeatureToggles.COSMO_CAT;

@Service
public class CosmoCatServiceImpl implements CosmoCatService {

    @Override
    @FeatureToggle(COSMO_CAT)
    public List<CosmoCat> getCosmoCats() {
        return List.of(
                CosmoCat.builder().id(1L).name("Barsik").breed("Balinese-Javanese").color("Red").build(),
                CosmoCat.builder().id(2L).name("Nugget").breed("Abyssinian").color("Black").build(),
                CosmoCat.builder().id(3L).name("Bean").breed("Bombay").color("White").build()
        );
    }
}
