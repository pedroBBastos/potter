package com.challenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;

/**
 * DTO usado como interface durante atualização de um personagem.
 * @author PedroBastos
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonagemUpdateDTO extends PersonagemDTO {

    @JsonIgnore
    private Date dataEdicao = new Date();

    @Builder
    public PersonagemUpdateDTO(String name,
                               String role,
                               String school,
                               String house,
                               String patronus) {
        super(name, role, school, house, patronus);
    }

    public static class PersonagemUpdateDTOBuilder extends PersonagemDTOBuilder {
        PersonagemUpdateDTOBuilder() {
            super();
        }
    }
}
