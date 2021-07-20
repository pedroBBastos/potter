package com.challenge.controller.personagem;

import com.challenge.dto.*;
import com.challenge.personagem.PersonagemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personagem")
public class PersonagemController {

    private PersonagemService personagemService;

    public PersonagemController(PersonagemService personagemService) {
        this.personagemService = personagemService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonagemDTO> savePersonagem(@RequestBody PersonagemCriacaoDTO personagemCriacaoDTO) {
        return new ResponseEntity<>(this.personagemService.criarNovoPersonagem(personagemCriacaoDTO), HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonagemDTO> updatePersonagem(@RequestBody PersonagemUpdateDTO personagemUpdateDTO) {
        return new ResponseEntity<>(this.personagemService.atualizarPersonagem(personagemUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deletePersonagem(@RequestBody PersonagemDTO personagemDTO) {
        this.personagemService.deletePersonagem(personagemDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonagemDTO>> findAll(@RequestParam(required = false) String house) {
        // TODO -> filtragem por diversos parametros pela URL: findAll(@RequestParam Map<String, String> params)
        return new ResponseEntity<>(this.personagemService.findAll(house), HttpStatus.OK);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonagemDTO> findByObject(@RequestBody PersonagemDTO personagemDTO) {
        return new ResponseEntity<>(this.personagemService.findByPersonagemDTO(personagemDTO), HttpStatus.OK);
    }
}
