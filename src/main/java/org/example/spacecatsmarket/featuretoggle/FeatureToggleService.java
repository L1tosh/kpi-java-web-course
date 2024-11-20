package org.example.spacecatsmarket.featuretoggle;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class FeatureToggleService {
    private final ConcurrentHashMap<String, Boolean> featureToggles;
    private static final String SUFFIX = ".enabled";

    public FeatureToggleService(FeatureToggleProperties featureToggleProperties) {
        featureToggles = new ConcurrentHashMap<>(featureToggleProperties.getToggles());
    }

    public boolean check(String featureName) {
        return featureToggles.getOrDefault(featureName + SUFFIX, false);
    }

    public void enable(String feature) {
        featureToggles.put(feature + SUFFIX, true);
    }

    public void disable(String feature) {
        featureToggles.put(feature + SUFFIX, false);
    }
}
