package org.example.spacecatsmarket.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CosmicWordValidator implements ConstraintValidator<CosmicWordCheck, String> {
    private static final List<String> COSMIC_WORDS = Arrays.asList("star", "galaxy", "comet", "planet", "nebula", "asteroid", "black hole", "supernova", "meteor", "space", "universe", "void", "orbital", "constellation", "lightyear", "quasar", "event horizon", "galactic", "solar", "lunar", "satellite", "astronaut", "starlight", "warp speed", "time travel", "wormhole", "cosmonaut", "extraterrestrial", "alien");


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return COSMIC_WORDS.stream().anyMatch(value.toLowerCase()::contains);
    }
}
