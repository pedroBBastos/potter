package com.challenge.personagem;

import com.challenge.data.DataGenerator;
import com.challenge.dto.PersonagemCriacaoDTO;
import com.challenge.dto.PersonagemDTO;
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
        verify(modelMapper, times(1)).map(any(), any());
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
        when(personagemRepository.existsByName("Harry Potter")).thenReturn(true);

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
        when(personagemRepository.findByName("Harry Potter"))
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
        when(personagemRepository.findByName(any()))
                .thenReturn(null);

        try {
            personagemService.atualizarPersonagem(personagemUpdateDTO);
            fail();
        } catch (PersonagemException exception) {
            assertEquals("Personagem não encontrado!", exception.getMessage());
        }

        verify(personagemRepository, times(0)).save(any(PersonagemEntity.class));
        verify(modelMapper, times(0)).map(any(), any());
    }

    @Test
    public void teste07_deveDeletarPersonagemComSucesso() {
        PersonagemDTO personagemDTO = DataGenerator.getPersonagemDTO();
        PersonagemEntity personagemEmBanco = DataGenerator.getPersonagem();
        when(personagemRepository.findByName("Harry Potter")).thenReturn(personagemEmBanco);

        personagemService.deletePersonagem(personagemDTO);

        verify(personagemRepository, times(1)).delete(any(PersonagemEntity.class));
    }

    @Test
    public void teste08_naoDeveDeletarPersonagemDTONulo() {

        try {
            personagemService.deletePersonagem(null);
            fail();
        } catch (ParametroInvalidoException exception) {
            assertEquals("Objeto nulo!", exception.getMessage());
        }

        verify(personagemRepository, times(0)).delete(any(PersonagemEntity.class));
        verify(modelMapper, times(0)).map(any(), any());
    }

    @Test
    public void teste09_deveValidarPersonagemExistenteEmRemocao() {
        PersonagemDTO personagemDTO = DataGenerator.getPersonagemDTO();
        when(personagemRepository.findByName(any()))
                .thenReturn(null);

        try {
            personagemService.deletePersonagem(personagemDTO);
            fail();
        } catch (PersonagemException exception) {
            assertEquals("Personagem não encontrado!", exception.getMessage());
        }

        verify(personagemRepository, times(0)).delete(any(PersonagemEntity.class));
        verify(modelMapper, times(0)).map(any(), any());
    }

    @Test
    public void teste10_deveValidarBuscaDeTodosOsPersonagens() {
        when(personagemRepository.findAll()).thenReturn(DataGenerator.getPersonagemList());
        personagemService.findAllPersonagemDTO(null);
        verify(personagemRepository, times(1)).findAll();
        verify(modelMapper, atLeast(1)).map(any(), any());
    }

    @Test
    public void teste11_deveValidarBuscaDeTodosOsPersonagensPorCasa() {
        when(personagemRepository.findAllByHouse("Gryffindor")).thenReturn(DataGenerator.getPersonagemList());
        personagemService.findAllPersonagemDTO("Gryffindor");
        verify(personagemRepository, times(1)).findAllByHouse("Gryffindor");
        verify(modelMapper, atLeast(1)).map(any(), any());
    }
}
