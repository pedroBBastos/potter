package com.challenge.entity;

import com.challenge.dto.PersonagemCriacaoDTO;
import com.challenge.dto.PersonagemDTO;
import com.challenge.dto.PersonagemUpdateDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(PersonagemEntity.class)
public class PersonagemEntityTest {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void teste01_PersonagemDTO() {
        PersonagemDTO personagemDTO = PersonagemDTO.builder()
                .name("Harry Porco").house("Grif").role("estud")
                .patronus("bla").school("escola").build();

        PersonagemEntity personagemEntity = modelMapper.map(personagemDTO, PersonagemEntity.class);

        assertEquals(personagemDTO.getName(), personagemEntity.getName());
        assertEquals(personagemDTO.getHouse(), personagemEntity.getHouse());
        assertEquals(personagemDTO.getRole(), personagemEntity.getRole());
        assertEquals(personagemDTO.getPatronus(), personagemEntity.getPatronus());
        assertEquals(personagemDTO.getSchool(), personagemEntity.getSchool());
    }

    @Test
    public void teste02_PersonagemCriacaoDTO() {
        PersonagemCriacaoDTO personagemCriacaoDTO = PersonagemCriacaoDTO.builder()
                .name("Harry Porco").house("Grif").role("estud")
                .patronus("bla").school("escola").build();

        PersonagemEntity personagemEntity = modelMapper.map(personagemCriacaoDTO, PersonagemEntity.class);

        assertEquals(personagemCriacaoDTO.getName(), personagemEntity.getName());
        assertEquals(personagemCriacaoDTO.getHouse(), personagemEntity.getHouse());
        assertEquals(personagemCriacaoDTO.getRole(), personagemEntity.getRole());
        assertEquals(personagemCriacaoDTO.getPatronus(), personagemEntity.getPatronus());
        assertEquals(personagemCriacaoDTO.getSchool(), personagemEntity.getSchool());
        assertEquals(personagemCriacaoDTO.getDataCriacao(), personagemEntity.getDataCriacao());
        assertEquals(personagemCriacaoDTO.getDataEdicao(), personagemEntity.getDataEdicao());
    }

    @Test
    public void teste03_PersonagemUpdateDTO() {
        PersonagemUpdateDTO personagemUpdateDTO = PersonagemUpdateDTO.builder()
                .name("Harry Porco").house("Grif").role("estud")
                .patronus("bla").school("escola").build();

        PersonagemEntity personagemEntity = modelMapper.map(personagemUpdateDTO, PersonagemEntity.class);

        assertEquals(personagemUpdateDTO.getName(), personagemEntity.getName());
        assertEquals(personagemUpdateDTO.getHouse(), personagemEntity.getHouse());
        assertEquals(personagemUpdateDTO.getRole(), personagemEntity.getRole());
        assertEquals(personagemUpdateDTO.getPatronus(), personagemEntity.getPatronus());
        assertEquals(personagemUpdateDTO.getSchool(), personagemEntity.getSchool());
        assertEquals(personagemUpdateDTO.getDataEdicao(), personagemEntity.getDataEdicao());
    }
}
