package com.challenge.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO base que representa um personagem. Criado para interfacear comunicação da API.
 * @author PedroBastos
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonagemDTO {

    @ApiModelProperty(value = "name", name = "name", dataType = "String", example = "Harry Potter")
    private String name;

    @ApiModelProperty(value = "role", name = "role", dataType = "String", example = "student")
    private String role;

    @ApiModelProperty(value = "school", name = "school", dataType = "String", example = "Hogwarts School of Witchcraft and Wizardry")
    private String school;

    @ApiModelProperty(value = "house", name = "house", dataType = "String", example = "1760529f-6d51-4cb1-bcb1-25087fce5bde")
    private String house;

    @ApiModelProperty(value = "patronus", name = "patronus", dataType = "String", example = "stag")
    private String patronus;
}
