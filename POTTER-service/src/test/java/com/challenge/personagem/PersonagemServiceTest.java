package com.challenge.personagem;

import com.challenge.client.PotterAPIHousesClient;
import com.challenge.data.DataGenerator;
import com.challenge.dto.PersonagemCriacaoDTO;
import com.challenge.dto.PersonagemDTO;
import com.challenge.dto.PersonagemUpdateDTO;
import com.challenge.entity.PersonagemEntity;
import com.challenge.exception.CasaException;
import com.challenge.exception.ParametroInvalidoException;
import com.challenge.exception.PersonagemException;
import com.challenge.repository.PersonagemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.powermock.core.classloader.annotations.PrepareForTest;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(PersonagemService.class)
public class PersonagemServiceTest {

    @Mock
    private PersonagemRepository personagemRepository;
    @Mock
    private PotterAPIHousesClient potterAPIHousesClient;
    @Spy
    private ModelMapper modelMapper;
    @Spy
    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    @InjectMocks
    private PersonagemService personagemService;

    @Captor
    private ArgumentCaptor<PersonagemEntity> personagemEntityCaptor;

    @Test
    public void teste01_deveSalvarPersonagemViaDTOComSucesso() {
        PersonagemCriacaoDTO personagemCriacaoDTO = DataGenerator.getPersonagemCriacaoDTO();
        when(personagemRepository.save(any())).thenReturn(DataGenerator.getPersonagem());
        when(potterAPIHousesClient.getHouses()).thenReturn(DataGenerator.getPotterHouses());

        personagemService.criarNovoPersonagem(personagemCriacaoDTO);

        verify(personagemRepository, times(1)).save(any(PersonagemEntity.class));
        verify(potterAPIHousesClient, times(1)).getHouses();
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
        when(potterAPIHousesClient.getHouses()).thenReturn(DataGenerator.getPotterHouses());

        personagemUpdateDTO.setPatronus("Patronous-updated");
        personagemService.atualizarPersonagem(personagemUpdateDTO);

        verify(personagemRepository).save(personagemEntityCaptor.capture());
        PersonagemEntity capturedPersonagem = personagemEntityCaptor.getValue();
        assertEquals("Patronous-updated", capturedPersonagem.getPatronus());
        verify(potterAPIHousesClient, times(1)).getHouses();
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
        personagemService.findAll(null);
        verify(personagemRepository, times(1)).findAll();
        verify(modelMapper, atLeast(1)).map(any(), any());
    }

    @Test
    public void teste11_deveValidarBuscaDeTodosOsPersonagensPorCasa() {
        when(personagemRepository.findAllByHouse("Gryffindor")).thenReturn(DataGenerator.getPersonagemList());
        personagemService.findAll("Gryffindor");
        verify(personagemRepository, times(1)).findAllByHouse("Gryffindor");
        verify(modelMapper, atLeast(1)).map(any(), any());
    }

    @Test
    public void teste12_naoDeveSalvarPersonagemDTOCasaInexistente() {

        PersonagemCriacaoDTO personagemCriacaoDTO = DataGenerator.getPersonagemCriacaoDTO();
        personagemCriacaoDTO.setHouse("casaAmarela");
        when(potterAPIHousesClient.getHouses()).thenReturn(DataGenerator.getPotterHouses());

        try {
            personagemService.criarNovoPersonagem(personagemCriacaoDTO);
            fail();
        } catch (CasaException exception) {
            assertEquals("Casa 'casaAmarela' não encontrada!", exception.getMessage());
        }

        verify(personagemRepository, times(0)).save(any(PersonagemEntity.class));
        verify(modelMapper, times(1)).map(any(), any());
        verify(potterAPIHousesClient, times(1)).getHouses();
    }

    @Test
    public void teste13_deveVerificarCOlunasNulasAoSalvarNovoPersonagem() {

        PersonagemCriacaoDTO personagemCriacaoDTO = DataGenerator.getPersonagemCriacaoColunasNulasDTO();

        try {
            personagemService.criarNovoPersonagem(personagemCriacaoDTO);
            fail();
        } catch (ParametroInvalidoException ignored) {}

        verify(personagemRepository, times(0)).save(any(PersonagemEntity.class));
        verify(modelMapper, times(1)).map(any(), any());
        verify(potterAPIHousesClient, times(0)).getHouses();
    }

    @Test
    public void teste14_deveValidarChaveNomeNaoInformadaEmAtualizacao() {
        PersonagemUpdateDTO personagemUpdateDTO = DataGenerator.getPersonagemUpdateDTO();
        personagemUpdateDTO.setName(null);

        try {
            personagemService.atualizarPersonagem(personagemUpdateDTO);
            fail();
        } catch (ParametroInvalidoException exception) {
            assertEquals("Chave 'name' não informada!", exception.getMessage());
        }

        verify(personagemRepository, times(0)).save(any(PersonagemEntity.class));
        verify(modelMapper, times(0)).map(any(), any());
    }

    @Test
    public void teste15_deveValidarChaveNomeNaoInformadaEmRemocao() {
        PersonagemDTO personagemDTO = DataGenerator.getPersonagemDTO();
        personagemDTO.setName(null);

        try {
            personagemService.deletePersonagem(personagemDTO);
            fail();
        } catch (ParametroInvalidoException exception) {
            assertEquals("Chave 'name' não informada!", exception.getMessage());
        }

        verify(personagemRepository, times(0)).delete(any(PersonagemEntity.class));
        verify(modelMapper, times(0)).map(any(), any());
    }

    @Test
    public void teste16_deveBuscarPersonagemPorDTOCOmSucesso() {
        PersonagemDTO personagemDTO = DataGenerator.getPersonagemDTO();
        PersonagemEntity personagemEmBanco = DataGenerator.getPersonagem();
        when(personagemRepository.findByName("Harry Potter"))
                .thenReturn(personagemEmBanco);

        personagemService.findByPersonagemDTO(personagemDTO);

        verify(personagemRepository, times(1)).findByName("Harry Potter");
        verify(modelMapper, times(1)).map(any(), any());
    }

    @Test
    public void teste17_deveValidarDTONuloAoBuscarPersonagemPorDTO() {

        try {
            personagemService.findByPersonagemDTO(null);
            fail();
        } catch (ParametroInvalidoException exception) {
            assertEquals("Objeto nulo!", exception.getMessage());
        }

        verify(personagemRepository, times(0)).findByName(any());
        verify(modelMapper, times(0)).map(any(), any());
    }

    @Test
    public void teste18_deveValidarNomeNuloAoBuscarPersonagemPorDTO() {

        PersonagemDTO personagemDTO = DataGenerator.getPersonagemDTO();
        personagemDTO.setName(null);

        try {
            personagemService.findByPersonagemDTO(personagemDTO);
            fail();
        } catch (ParametroInvalidoException exception) {
            assertEquals("Chave 'name' não informada!", exception.getMessage());
        }

        verify(personagemRepository, times(0)).findByName(any());
        verify(modelMapper, times(0)).map(any(), any());
    }
}
