package io.teamsgroup.caso_1_programacion_concurrente.service.sensor;

import io.teamsgroup.caso_1_programacion_concurrente.domain.Evento;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorAcceso;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorMovimiento;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorTemperatura;
import io.teamsgroup.caso_1_programacion_concurrente.model.EventoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.repos.EventoRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.SensorAccesoRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.SensorMovimientoRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.SensorTemperaturaRepository;
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

public class EventoServiceTest {

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private SensorMovimientoRepository sensorMovimientoRepository;

    @Mock
    private SensorTemperaturaRepository sensorTemperaturaRepository;

    @Mock
    private SensorAccesoRepository sensorAccesoRepository;

    @InjectMocks
    private EventoService eventoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Evento evento = new Evento();
        evento.setId(1);
        evento.setNombre("Evento Test");
        evento.setToken("TOKEN_EVENTO_1");

        when(eventoRepository.findAll(Sort.by("id"))).thenReturn(List.of(evento));

        List<EventoDTO> eventos = eventoService.findAll("TOKEN_EVENTO_1");

        assertNotNull(eventos);
        assertEquals(1, eventos.size());
        assertEquals("Evento Test", eventos.get(0).getNombre());
    }

    @Test
    public void testGetEventoById() {
        Evento evento = new Evento();
        evento.setId(1);
        evento.setNombre("Evento Test");

        when(eventoRepository.findById(1)).thenReturn(Optional.of(evento));

        EventoDTO eventoDTO = eventoService.get(1);

        assertNotNull(eventoDTO);
        assertEquals("Evento Test", eventoDTO.getNombre());
    }

    @Test
    public void testGetEventoById_NotFound() {
        when(eventoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> eventoService.get(1));
    }

    @Test
    public void testCreateEvento() {
        Evento evento = new Evento();
        evento.setId(1);

        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setNombre("Evento Test");

        Integer id = eventoService.create(eventoDTO);

        assertNotNull(id);
        assertEquals(1, id);
    }

    @Test
    public void testDeleteEvento() {
        doNothing().when(eventoRepository).deleteById(1);

        eventoService.delete(1);

        verify(eventoRepository, times(1)).deleteById(1);
    }
}
