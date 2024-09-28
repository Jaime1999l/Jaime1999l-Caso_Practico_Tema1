package io.teamsgroup.caso_1_programacion_concurrente.service.sensor;

import io.teamsgroup.caso_1_programacion_concurrente.domain.Evento;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorMovimiento;
import io.teamsgroup.caso_1_programacion_concurrente.domain.Usuario;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorMovimientoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.repos.EventoRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.SensorMovimientoRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.UsuarioRepository;
import io.teamsgroup.caso_1_programacion_concurrente.util.NotFoundException;
import io.teamsgroup.caso_1_programacion_concurrente.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorMovimientoService {

    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public SensorMovimientoService(final SensorMovimientoRepository sensorMovimientoRepository,
                                   final UsuarioRepository usuarioRepository,
                                   final EventoRepository eventoRepository) {
        this.sensorMovimientoRepository = sensorMovimientoRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<SensorMovimientoDTO> findAll(String token) {
        List<SensorMovimiento> sensorMovimientos = sensorMovimientoRepository.findAll(Sort.by("id"));


        System.out.println("List of all sensors: " + sensorMovimientos.size()); // Verificar cuántos sensores se cargaron
        System.out.println("Searching sensors with token: " + token); // Verificar el token buscado

        // Verificamos el token proporcionado
        boolean tokenValido = sensorMovimientos.stream()
                .anyMatch(sensor -> sensor.getToken().equals(token));

        // Si el token no coincide con ninguno de los sensores, se lanza excepción
        if (!tokenValido) {
            System.out.println("Token inválido para sensores de movimiento.");
        }

        // Filtramos los sensores que coinciden con el token proporcionado
        List<SensorMovimiento> sensoresConToken = sensorMovimientos.stream()
                .filter(sensor -> sensor.getToken().equals(token))
                .toList();

        return sensoresConToken.stream()
                .map(sensor -> mapToDTO(sensor, new SensorMovimientoDTO()))
                .toList();
    }


    public SensorMovimientoDTO get(final Integer id) {
        return sensorMovimientoRepository.findById(id)
                .map(sensorMovimiento -> mapToDTO(sensorMovimiento, new SensorMovimientoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final SensorMovimientoDTO sensorMovimientoDTO) {
        SensorMovimiento sensorMovimiento = new SensorMovimiento();
        sensorMovimiento.setNombre(sensorMovimientoDTO.getNombre());
        sensorMovimiento.setDatosMovimiento(sensorMovimientoDTO.getDatosMovimiento());
        sensorMovimiento.setNotificacion(sensorMovimientoDTO.getNotificacion());
        sensorMovimiento.setToken(sensorMovimientoDTO.getToken());
        System.out.println("Token generado para Sensor de Movimiento: " + sensorMovimiento.getToken()); // Muestra el token en consola
        return sensorMovimientoRepository.save(sensorMovimiento).getId();
    }

    public void update(final Integer id, final SensorMovimientoDTO sensorMovimientoDTO) {
        final SensorMovimiento sensorMovimiento = sensorMovimientoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sensorMovimientoDTO, sensorMovimiento);
        sensorMovimiento.setToken(generateToken("MOVIMIENTO"));
        sensorMovimientoRepository.save(sensorMovimiento);
    }

    public void delete(final Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser null");
        }
        sensorMovimientoRepository.deleteById(id);
    }

    private SensorMovimientoDTO mapToDTO(final SensorMovimiento sensorMovimiento,
                                         final SensorMovimientoDTO sensorMovimientoDTO) {
        sensorMovimientoDTO.setId(sensorMovimiento.getId());
        sensorMovimientoDTO.setNombre(sensorMovimiento.getNombre());
        sensorMovimientoDTO.setNotificacion(sensorMovimiento.getNotificacion());
        sensorMovimientoDTO.setDatosMovimiento(sensorMovimiento.getDatosMovimiento());
        sensorMovimientoDTO.setSensoresMovimiento(sensorMovimiento.getSensoresMovimiento() == null
                ? null
                : sensorMovimiento.getSensoresMovimiento().getId());
        sensorMovimientoDTO.setToken(sensorMovimiento.getToken());
        return sensorMovimientoDTO;
    }

    private SensorMovimiento mapToEntity(final SensorMovimientoDTO sensorMovimientoDTO,
                                         final SensorMovimiento sensorMovimiento) {
        sensorMovimiento.setNombre(sensorMovimientoDTO.getNombre());
        sensorMovimiento.setNotificacion(sensorMovimientoDTO.getNotificacion());
        sensorMovimiento.setDatosMovimiento(sensorMovimientoDTO.getDatosMovimiento());
        final Usuario usuarioAsociado = sensorMovimientoDTO.getSensoresMovimiento() == null
                ? null
                : usuarioRepository.findById(sensorMovimientoDTO.getSensoresMovimiento())
                .orElseThrow(() -> new NotFoundException("Usuario asociado no encontrado"));
        sensorMovimiento.setSensoresMovimiento(usuarioAsociado);
        return sensorMovimiento;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final SensorMovimiento sensorMovimiento = sensorMovimientoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Evento eventosMovimientoEvento = eventoRepository.findFirstByEventosMovimiento(sensorMovimiento);
        if (eventosMovimientoEvento != null) {
            referencedWarning.setKey("sensorMovimiento.evento.eventosMovimiento.referenced");
            referencedWarning.addParam(eventosMovimientoEvento.getId());
            return referencedWarning;
        }
        return null;
    }

    private String generateToken(String tipo) {
        // Genera un token genérico sin el nombre específico del sensor
        return tipo + "_Sensor";
    }
}
