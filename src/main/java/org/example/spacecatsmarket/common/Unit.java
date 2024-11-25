package org.example.spacecatsmarket.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Unit {
    GRAM("grams"),
    LITER("liters"),
    METER("meters"),
    UNIT("units");

    private final String displayName;

}
