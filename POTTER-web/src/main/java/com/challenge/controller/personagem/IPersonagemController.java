package com.challenge.controller.personagem;

import com.challenge.config.SpringFoxConfig;
import com.challenge.dto.PersonagemCriacaoDTO;
import com.challenge.dto.PersonagemDTO;
import com.challenge.dto.PersonagemUpdateDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = SpringFoxConfig.TAG)
public interface IPersonagemController {


    @ApiOperation(value = "Save Personagem",
            notes = "Endpoint para salvar novo personagem. Chave de unicidade considerada é o nome " +
                    "do personagem.")
    ResponseEntity<PersonagemDTO> savePersonagem(PersonagemCriacaoDTO personagemCriacaoDTO);

    @ApiOperation(value = "Update Personagem",
            notes = "Endpoint para atualizar novo personagem. Chave de unicidade considerada é o nome " +
                    "do personagem. Assim, é considerado que pode haver troca de casas.")
    ResponseEntity<PersonagemDTO> updatePersonagem(PersonagemUpdateDTO personagemUpdateDTO);

    @ApiOperation(value = "Delete Personagem",
            notes = "Endpoint para deletar um personagem. Chave de unicidade considerada é o nome " +
                    "do personagem.")
    ResponseEntity deletePersonagem(PersonagemDTO personagemDTO);

    @ApiOperation(value = "Find All Personagens",
            notes = "Endpoint para buscar todos os personagens. Contém filtro de casa.")
    ResponseEntity<List<PersonagemDTO>> findAll(String house);

    @ApiOperation(value = "Find All Personagens by PersonagemDTO",
            notes = "Endpoint para buscar personagem por DTO. Chave de unicidade considerada é o nome " +
                    "do personagem.")
    ResponseEntity<PersonagemDTO> findByObject(PersonagemDTO personagemDTO);
}
