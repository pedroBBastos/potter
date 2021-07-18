package com.challenge.personagem;

import com.challenge.data.DataGenerator;
import com.challenge.entity.PersonagemEntity;
import com.challenge.repository.PersonagemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(PersonagemService.class)
public class PersonagemServiceTest {

    @Mock
    private PersonagemRepository personagemRepository;
    @InjectMocks
    private PersonagemService personagemService;

    @Test
    public void teste01_deveSalvarNovoUsuarioComSucesso() {
        PersonagemEntity personagemEntity = DataGenerator.getNovoPersonagem();
        personagemService.save(personagemEntity);
        verify(personagemRepository, times(1)).save(any(PersonagemEntity.class));
    }

    @Test
    public void teste02_naoDeveSalvarPersonagemNulo() {
        PersonagemEntity personagemEntity = null;
        when(personagemRepository.save(isNull()))
                .thenThrow(new IllegalArgumentException("Entity must not be null."));

        try {
            personagemService.save(personagemEntity);
            fail();
        } catch (IllegalArgumentException exception) {
            assertEquals("Entity must not be null.", exception.getMessage());
        }

        verify(personagemRepository, times(1)).save(isNull());
    }

    @Test
    public void teste03_deveBuscarTodosOsRegistrosdePersonagemComSucesso() {
        when(personagemRepository.findAll()).thenReturn(List.of(DataGenerator.getPersonagem()));
        personagemService.findAll();
        verify(personagemRepository, times(1)).findAll();
    }

    @Test
    public void teste04_deveBuscarORegistroDoPersonagemDeDadoIdComSucesso() {
        PersonagemEntity personagemEntity = DataGenerator.getPersonagem();
        when(personagemRepository.findById(1L)).thenReturn(Optional.of(personagemEntity));

        PersonagemEntity byId = personagemService.findById(1L);

        assertNotNull(byId);
        assertEquals(1L, byId.getId().longValue());
    }

    @Test
    public void teste05_deveRetornarNuloCasoPersonagemNaoExista() {
        when(personagemRepository.findById(2L)).thenReturn(Optional.empty());
        PersonagemEntity byId = personagemService.findById(2L);
        assertNull(byId);
    }

    @Test
    public void teste06_deveVerificarIdNuloEmBuscaPorId() {
        when(personagemRepository.findById(isNull()))
                .thenThrow(new IllegalArgumentException("The given id must not be null!"));

        try {
            personagemService.findById(null);
            fail();
        } catch (IllegalArgumentException exception) {
            assertEquals("The given id must not be null!", exception.getMessage());
        }

        verify(personagemRepository, times(1)).findById(isNull());
    }

    @Test
    public void teste07_deveAtualizarPersonagemComSUcesso() {
        PersonagemEntity personagemEntity = DataGenerator.getPersonagem();
        personagemEntity.setPatronus("Frubas");

        personagemService.save(personagemEntity);
        verify(personagemRepository, times(1)).save(any(PersonagemEntity.class));
    }

    @Test
    public void teste08_deveDeletarUsuarioComSucesso() {
        PersonagemEntity personagemEntity = DataGenerator.getPersonagem();
        personagemService.delete(personagemEntity);
        verify(personagemRepository, times(1)).delete(any(PersonagemEntity.class));
    }

    @Test
    public void teste09_naoDeveDeletarPersonagemNulo() {
        PersonagemEntity personagemEntity = null;
        doThrow(new IllegalArgumentException("Entity must not be null."))
                .when(personagemRepository).delete(isNull());

        try {
            personagemService.delete(personagemEntity);
            fail();
        } catch (IllegalArgumentException exception) {
            assertEquals("Entity must not be null.", exception.getMessage());
        }

        verify(personagemRepository, times(1)).delete(isNull());
    }

    @Test
    public void teste09_deveDeletarPersonagemPorIdComSucesso() {
        personagemService.deleteById(1L);
        verify(personagemRepository, times(1)).deleteById(1L);
    }

    @Test
    public void teste10_deveTratarDeleteIdNulo() {

        doThrow(new IllegalArgumentException("The given id must not be null!"))
                .when(personagemRepository).deleteById(isNull());

        try {
            personagemService.deleteById(null);
            fail();
        } catch (IllegalArgumentException exception) {
            assertEquals("The given id must not be null!", exception.getMessage());
        }

        verify(personagemRepository, times(1)).deleteById(isNull());
    }
}
