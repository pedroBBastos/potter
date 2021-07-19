package com.challenge.personagem;

import com.challenge.data.DataGenerator;
import com.challenge.dto.PersonagemCriacaoDTO;
import com.challenge.dto.PersonagemUpdateDTO;
import com.challenge.entity.PersonagemEntity;
import com.challenge.exception.ParametroInvalidoException;
import com.challenge.exception.PersonagemException;
import com.challenge.repository.PersonagemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
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

    @Captor
    private ArgumentCaptor<PersonagemEntity> personagemEntityCaptor;

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

    @Test
    public void teste04_deveAtualizarPersonagemComSucesso() {
        PersonagemUpdateDTO personagemUpdateDTO = DataGenerator.getPersonagemUpdateDTO();
        PersonagemEntity personagemEmBanco = DataGenerator.getPersonagem();
        when(personagemRepository.existsByNameAndHouse("Harry Potter","Gryffindor"))
                .thenReturn(true);
        when(personagemRepository.findByNameAndHouse("Harry Potter","Gryffindor"))
                .thenReturn(personagemEmBanco);
        when(personagemRepository.save(any())).thenReturn(DataGenerator.getPersonagem());

        personagemUpdateDTO.setPatronus("Patronous-updated");
        personagemService.atualizarPersonagem(personagemUpdateDTO);

        verify(personagemRepository).save(personagemEntityCaptor.capture());

        PersonagemEntity capturedPersonagem = personagemEntityCaptor.getValue();
        assertEquals("Patronous-updated", capturedPersonagem.getPatronus());
    }

    @Test
    public void teste05_naoDeveAtualizarPersonagemUpdateDTONulo() {

        try {
            personagemService.atualizarPersonagem(null);
            fail();
        } catch (ParametroInvalidoException exception) {
            assertEquals("Objeto nulo!", exception.getMessage());
        }

        verify(personagemRepository, times(0)).save(any(PersonagemEntity.class));
        verify(modelMapper, times(0)).map(any(), any());
    }

    @Test
    public void teste06_deveValidarPersonagemExistenteEmAtualizacao() {
        PersonagemUpdateDTO personagemUpdateDTO = DataGenerator.getPersonagemUpdateDTO();
        when(personagemRepository.existsByNameAndHouse("Harry Potter","Gryffindor"))
                .thenReturn(false);

        try {
            personagemService.atualizarPersonagem(personagemUpdateDTO);
            fail();
        } catch (PersonagemException exception) {
            assertEquals("Personagem não encontrado!", exception.getMessage());
        }

        verify(personagemRepository, times(0)).save(any(PersonagemEntity.class));
        verify(modelMapper, times(0)).map(any(), any());
    }
}
