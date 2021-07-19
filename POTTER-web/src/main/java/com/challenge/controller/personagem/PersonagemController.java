package com.challenge.controller.personagem;

import com.challenge.dto.PersonagemCriacaoDTO;
import com.challenge.dto.PersonagemDTO;
import com.challenge.dto.PersonagemUpdateDTO;
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
        // TODO
        return null;
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deletePersonagem(@RequestBody PersonagemDTO personagemDTO) {
        // TODO
        return null;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonagemDTO>> findAll() {
        // TODO
        return null;
    }

    @GetMapping(value = "{house}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonagemDTO> findByHouse(@PathVariable(value = "house") String house) {
        // TODO
        return null;
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonagemDTO> findByObject(@RequestBody PersonagemCriacaoDTO personagemCriacaoDTO) {
        // TODO
        return null;
    }
}
