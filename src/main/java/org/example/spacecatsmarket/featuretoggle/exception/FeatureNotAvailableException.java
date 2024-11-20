package org.example.spacecatsmarket.featuretoggle.exception;

public class FeatureNotAvailableException extends RuntimeException {
    private static final String FEATURE_NOT_AVAILABLE_MESSAGE = "The feature is not available.";

    public FeatureNotAvailableException() {
        super(FEATURE_NOT_AVAILABLE_MESSAGE);
    }
}
