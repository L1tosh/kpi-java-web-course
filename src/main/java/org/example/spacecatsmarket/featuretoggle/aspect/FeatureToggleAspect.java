package org.example.spacecatsmarket.featuretoggle.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.spacecatsmarket.featuretoggle.FeatureToggleService;
import org.example.spacecatsmarket.featuretoggle.annotation.FeatureToggle;
import org.example.spacecatsmarket.featuretoggle.exception.FeatureNotAvailableException;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    @Before(value = "@annotation(featureToggle)")
    public void checkFeatureToggleAnnotation(FeatureToggle featureToggle) {
        var toggle = featureToggle.value().getName();

        if (featureToggleService.check(toggle)) {
            return;
        }

        log.warn("Feature toggle {} is not enabled!", toggle);
        throw new FeatureNotAvailableException();
    }

}
