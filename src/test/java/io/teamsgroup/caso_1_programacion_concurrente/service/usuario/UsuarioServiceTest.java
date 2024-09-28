package io.teamsgroup.caso_1_programacion_concurrente.service.usuario;

import io.teamsgroup.caso_1_programacion_concurrente.domain.Credenciales;
import io.teamsgroup.caso_1_programacion_concurrente.domain.Rol;
import io.teamsgroup.caso_1_programacion_concurrente.domain.Usuario;
import io.teamsgroup.caso_1_programacion_concurrente.model.UsuarioDTO;
import io.teamsgroup.caso_1_programacion_concurrente.repos.CredencialesRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.RolRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.SensorAccesoRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.SensorMovimientoRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.SensorTemperaturaRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.UsuarioRepository;
import io.teamsgroup.caso_1_programacion_concurrente.util.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private CredencialesRepository credencialesRepository;

    @Mock
    private SensorMovimientoRepository sensorMovimientoRepository;

    @Mock
    private SensorTemperaturaRepository sensorTemperaturaRepository;

    @Mock
    private SensorAccesoRepository sensorAccesoRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("John");
        usuario.setApellido1("Doe");
        usuario.setCorreo("john.doe@example.com");

        when(usuarioRepository.findAll(Sort.by("id"))).thenReturn(List.of(usuario));

        List<UsuarioDTO> usuarios = usuarioService.findAll();

        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals("John", usuarios.get(0).getNombre());
        assertEquals("Doe", usuarios.get(0).getApellido1());
        assertEquals("john.doe@example.com", usuarios.get(0).getCorreo());
    }

    @Test
    public void testGetUsuarioById() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("John");
        usuario.setApellido1("Doe");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        UsuarioDTO usuarioDTO = usuarioService.get(1);

        assertNotNull(usuarioDTO);
        assertEquals("John", usuarioDTO.getNombre());
        assertEquals("Doe", usuarioDTO.getApellido1());
    }

    @Test
    public void testGetUsuarioById_NotFound() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> usuarioService.get(1));
    }

    @Test
    public void testCreateUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre("John");
        usuarioDTO.setApellido1("Doe");
        usuarioDTO.setCorreo("john.doe@example.com");

        Integer id = usuarioService.create(usuarioDTO);

        assertNotNull(id);
        assertEquals(1, id);
    }

    @Test
    public void testUpdateUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("John");
        usuario.setApellido1("Doe");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre("John Updated");
        usuarioDTO.setApellido1("Doe Updated");

        usuarioService.update(1, usuarioDTO);

        assertEquals("John Updated", usuario.getNombre());
        assertEquals("Doe Updated", usuario.getApellido1());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void testDeleteUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).deleteById(1);

        usuarioService.delete(1);

        verify(usuarioRepository, times(1)).deleteById(1);
    }

    @Test
    public void testUsuarioExists() {
        when(usuarioRepository.existsById(1)).thenReturn(true);

        boolean exists = usuarioService.usuarioExists(1);

        assertTrue(exists);
    }

    @Test
    public void testUsuarioNotExists() {
        when(usuarioRepository.existsById(1)).thenReturn(false);

        boolean exists = usuarioService.usuarioExists(1);

        assertFalse(exists);
    }
}
