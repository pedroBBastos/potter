package com.challenge;

import com.challenge.data.DataGenerator;
import com.challenge.entity.PersonagemEntity;
import com.challenge.exception.ParametroInvalidoException;
import com.challenge.personagem.PersonagemService;
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
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(CrudService.class)
public class CrudServiceTest {

    @Mock
    private PersonagemRepository crudRepository;
    @InjectMocks
    private PersonagemService crudService;

    @Test
    public void teste01_deveSalvarNovoUsuarioComSucesso() {
        PersonagemEntity personagemEntity = DataGenerator.getNovoPersonagem();
        crudService.save(personagemEntity);
        verify(crudRepository, times(1)).save(any(PersonagemEntity.class));
    }

    @Test
    public void teste02_naoDeveSalvarPersonagemNulo() {

        try {
            crudService.save(null);
            fail();
        } catch (ParametroInvalidoException exception) {
            assertEquals("Entidade fornecida não pode ser nula", exception.getMessage());
        }

        verify(crudRepository, times(0)).save(isNull());
    }

    @Test
    public void teste03_deveBuscarTodosOsRegistrosdePersonagemComSucesso() {
        when(crudRepository.findAll()).thenReturn(List.of(DataGenerator.getPersonagem()));
        crudService.findAll();
        verify(crudRepository, times(1)).findAll();
    }

    @Test
    public void teste04_deveBuscarORegistroDoPersonagemDeDadoIdComSucesso() {
        PersonagemEntity personagemEntity = DataGenerator.getPersonagem();
        when(crudRepository.findById(1L)).thenReturn(Optional.of(personagemEntity));

        PersonagemEntity byId = crudService.findById(1L);

        assertNotNull(byId);
        assertEquals(1L, byId.getId().longValue());
    }

    @Test
    public void teste05_deveRetornarNuloCasoPersonagemNaoExista() {
        when(crudRepository.findById(2L)).thenReturn(Optional.empty());
        PersonagemEntity byId = crudService.findById(2L);
        assertNull(byId);
    }

    @Test
    public void teste06_deveVerificarIdNuloEmBuscaPorId() {
        try {
            crudService.findById(null);
            fail();
        } catch (ParametroInvalidoException exception) {
            assertEquals("Id fornecido não pode ser nulo", exception.getMessage());
        }

        verify(crudRepository, times(0)).findById(isNull());
    }

    @Test
    public void teste07_deveAtualizarPersonagemComSUcesso() {
        PersonagemEntity personagemEntity = DataGenerator.getPersonagem();
        personagemEntity.setPatronus("Frubas");

        crudService.save(personagemEntity);
        verify(crudRepository, times(1)).save(any(PersonagemEntity.class));
    }

    @Test
    public void teste08_deveDeletarUsuarioComSucesso() {
        PersonagemEntity personagemEntity = DataGenerator.getPersonagem();
        crudService.delete(personagemEntity);
        verify(crudRepository, times(1)).delete(any(PersonagemEntity.class));
    }

    @Test
    public void teste09_naoDeveDeletarPersonagemNulo() {
        try {
            crudService.delete(null);
            fail();
        } catch (ParametroInvalidoException exception) {
            assertEquals("Entidade fornecida não pode ser nula", exception.getMessage());
        }

        verify(crudRepository, times(0)).delete(isNull());
    }

    @Test
    public void teste10_deveDeletarPersonagemPorIdComSucesso() {
        crudService.deleteById(1L);
        verify(crudRepository, times(1)).deleteById(1L);
    }

    @Test
    public void teste11_deveTratarDeleteIdNulo() {
        try {
            crudService.deleteById(null);
            fail();
        } catch (ParametroInvalidoException exception) {
            assertEquals("Id fornecido não pode ser nulo", exception.getMessage());
        }

        verify(crudRepository, times(0)).deleteById(isNull());
    }
}
