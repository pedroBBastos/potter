package com.challenge.exceptionhandler;

import lombok.Builder;
import lombok.Data;

/**
 * Classe para encapsular detalhes de retorno de tratamentos de erro.
 * @author PedroBastos
 */
@Data
@Builder
public class ErrorMessage {
    private String message;
    private String details;

}
