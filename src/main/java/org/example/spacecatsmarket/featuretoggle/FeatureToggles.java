package org.example.spacecatsmarket.featuretoggle;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeatureToggles {
    COSMO_CAT("cosmoCats");

    private final String name;
}
