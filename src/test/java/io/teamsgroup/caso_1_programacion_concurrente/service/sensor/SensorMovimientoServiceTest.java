package io.teamsgroup.caso_1_programacion_concurrente.service.sensor;

import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorMovimiento;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorMovimientoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.repos.SensorMovimientoRepository;
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

public class SensorMovimientoServiceTest {

    @Mock
    private SensorMovimientoRepository sensorMovimientoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private SensorMovimientoService sensorMovimientoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        SensorMovimiento sensor = new SensorMovimiento();
        sensor.setId(1);
        sensor.setNombre("Sensor Movimiento Test");
        sensor.setToken("TOKEN_MOVIMIENTO_1");

        when(sensorMovimientoRepository.findAll(Sort.by("id"))).thenReturn(List.of(sensor));

        List<SensorMovimientoDTO> sensores = sensorMovimientoService.findAll("TOKEN_MOVIMIENTO_1");

        assertNotNull(sensores);
        assertEquals(1, sensores.size());
        assertEquals("Sensor Movimiento Test", sensores.get(0).getNombre());
    }

    @Test
    public void testGetSensorById() {
        SensorMovimiento sensor = new SensorMovimiento();
        sensor.setId(1);
        sensor.setNombre("Sensor Movimiento Test");

        when(sensorMovimientoRepository.findById(1)).thenReturn(Optional.of(sensor));

        SensorMovimientoDTO sensorDTO = sensorMovimientoService.get(1);

        assertNotNull(sensorDTO);
        assertEquals("Sensor Movimiento Test", sensorDTO.getNombre());
    }

    @Test
    public void testGetSensorById_NotFound() {
        when(sensorMovimientoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sensorMovimientoService.get(1));
    }

    @Test
    public void testCreateSensor() {
        SensorMovimiento sensor = new SensorMovimiento();
        sensor.setId(1);

        when(sensorMovimientoRepository.save(any(SensorMovimiento.class))).thenReturn(sensor);

        SensorMovimientoDTO sensorDTO = new SensorMovimientoDTO();
        sensorDTO.setNombre("Sensor Movimiento Test");
        sensorDTO.setToken("TOKEN_MOVIMIENTO_1");

        Integer id = sensorMovimientoService.create(sensorDTO);

        assertNotNull(id);
        assertEquals(1, id);
    }

    @Test
    public void testDeleteSensor() {
        doNothing().when(sensorMovimientoRepository).deleteById(1);

        sensorMovimientoService.delete(1);

        verify(sensorMovimientoRepository, times(1)).deleteById(1);
    }
}
