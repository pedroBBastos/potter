package com.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonagemDTO {

    private String name;
    private String role;
    private String school;
    private String house;
    private String patronus;
}
