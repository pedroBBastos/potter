package com.challenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;

/**
 * DTO usado como interface durante criação de um personagem.
 * @author PedroBastos
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonagemCriacaoDTO extends PersonagemDTO {

    @JsonIgnore
    private Date dataCriacao = new Date();

    @JsonIgnore
    private Date dataEdicao = new Date();

    @Builder
    public PersonagemCriacaoDTO(String name,
                                String role,
                                String school,
                                String house,
                                String patronus) {
        super(name, role, school, house, patronus);
    }

    public static class PersonagemCriacaoDTOBuilder extends PersonagemDTOBuilder {
        PersonagemCriacaoDTOBuilder() {
            super();
        }
    }
}
