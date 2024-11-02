package org.example.spacecatsmarket.web.exception;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorDetail {
    int status;
    String error;
    String message;
    String path;
}
