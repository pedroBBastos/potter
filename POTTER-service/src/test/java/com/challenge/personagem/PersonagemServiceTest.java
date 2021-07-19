package com.challenge.personagem;

import com.challenge.data.DataGenerator;
import com.challenge.dto.PersonagemCriacaoDTO;
import com.challenge.entity.PersonagemEntity;
import com.challenge.exception.ParametroInvalidoException;
import com.challenge.exception.PersonagemException;
import com.challenge.repository.PersonagemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(PersonagemService.class)
public class PersonagemServiceTest {

    @Mock
    private PersonagemRepository personagemRepository;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private PersonagemService personagemService;

    @Test
    public void teste01_deveSalvarPersonagemViaDTOComSucesso() {
        PersonagemCriacaoDTO personagemCriacaoDTO = DataGenerator.getPersonagemCriacaoDTO();
        when(personagemRepository.save(any())).thenReturn(DataGenerator.getPersonagem());

        personagemService.criarNovoPersonagem(personagemCriacaoDTO);

        verify(personagemRepository, times(1)).save(any(PersonagemEntity.class));
        verify(modelMapper, times(2)).map(any(), any());
    }

    @Test
    public void teste02_naoDeveSalvarPersonagemDTONulo() {

        try {
            personagemService.criarNovoPersonagem(null);
            fail();
        } catch (ParametroInvalidoException exception) {
            assertEquals("Objeto nulo!", exception.getMessage());
        }

        verify(personagemRepository, times(0)).save(any(PersonagemEntity.class));
        verify(modelMapper, times(0)).map(any(), any());
    }

    @Test
    public void teste03_naoDeveSalvarPersonagemDTOJaExistente() {
        PersonagemCriacaoDTO personagemCriacaoDTO = DataGenerator.getPersonagemCriacaoDTO();
        when(personagemRepository.existsByNameAndHouse("Harry Potter","Gryffindor"))
                .thenReturn(true);

        try {
            personagemService.criarNovoPersonagem(personagemCriacaoDTO);
            fail();
        } catch (PersonagemException exception) {
            assertEquals("Personagem já existente!", exception.getMessage());
        }

        verify(personagemRepository, times(0)).save(any(PersonagemEntity.class));
        verify(modelMapper, times(0)).map(any(), any());
    }
}
