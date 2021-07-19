package com.challenge.exceptionhandler;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {
    private String message;
    private String details;

}
