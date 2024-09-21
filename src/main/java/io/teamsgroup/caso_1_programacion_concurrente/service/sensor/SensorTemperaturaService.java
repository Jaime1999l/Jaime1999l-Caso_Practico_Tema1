package io.teamsgroup.caso_1_programacion_concurrente.service.sensor;

import io.teamsgroup.caso_1_programacion_concurrente.domain.Evento;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorMovimiento;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorTemperatura;
import io.teamsgroup.caso_1_programacion_concurrente.domain.Usuario;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorMovimientoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorTemperaturaDTO;
import io.teamsgroup.caso_1_programacion_concurrente.repos.EventoRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.SensorTemperaturaRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.UsuarioRepository;
import io.teamsgroup.caso_1_programacion_concurrente.util.NotFoundException;
import io.teamsgroup.caso_1_programacion_concurrente.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorTemperaturaService {

    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public SensorTemperaturaService(final SensorTemperaturaRepository sensorTemperaturaRepository,
                                    final UsuarioRepository usuarioRepository,
                                    final EventoRepository eventoRepository) {
        this.sensorTemperaturaRepository = sensorTemperaturaRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<SensorTemperaturaDTO> findAll(String token) {
        List<SensorTemperatura> sensorAccesos = sensorTemperaturaRepository.findAll(Sort.by("id"));

        // Filtrar los sensores que tienen el token proporcionado
        List<SensorTemperatura> sensoresConToken = sensorAccesos.stream()
                .filter(sensor -> sensor.getToken().equals(token))
                .toList();

        // Si no hay sensores con el token, lanzar excepción
        if (sensoresConToken.isEmpty()) {
            System.out.println("Token inválido para sensores de temperatura."); // Muestra mensaje en consola
        }

        // Convertir los sensores filtrados a DTO
        return sensoresConToken.stream()
                .map(sensorAcceso -> mapToDTO(sensorAcceso, new SensorTemperaturaDTO()))
                .toList();
    }


    public SensorTemperaturaDTO get(final Integer id) {
        return sensorTemperaturaRepository.findById(id)
                .map(sensorTemperatura -> mapToDTO(sensorTemperatura, new SensorTemperaturaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final SensorTemperaturaDTO sensorTemperaturaDTO) {
        SensorTemperatura sensorTemperatura = new SensorTemperatura();
        sensorTemperatura.setNombre(sensorTemperaturaDTO.getNombre());
        sensorTemperatura.setDatosTemperatura(sensorTemperaturaDTO.getDatosTemperatura());
        sensorTemperatura.setNotificacion(sensorTemperaturaDTO.getNotificacion());
        sensorTemperatura.setToken(sensorTemperaturaDTO.getToken());
        System.out.println("Token generado para Sensor de Temperatura: " + sensorTemperatura.getToken()); // Muestra el token en consola
        return sensorTemperaturaRepository.save(sensorTemperatura).getId();
    }

    public void update(final Integer id, final SensorTemperaturaDTO sensorTemperaturaDTO) {
        final SensorTemperatura sensorTemperatura = sensorTemperaturaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sensorTemperaturaDTO, sensorTemperatura);
        sensorTemperatura.setToken(generateToken("TEMPERATURA"));
        sensorTemperaturaRepository.save(sensorTemperatura);
    }

    public void delete(final Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser null");
        }
        sensorTemperaturaRepository.deleteById(id);
    }

    private SensorTemperaturaDTO mapToDTO(final SensorTemperatura sensorTemperatura,
                                          final SensorTemperaturaDTO sensorTemperaturaDTO) {
        sensorTemperaturaDTO.setId(sensorTemperatura.getId());
        sensorTemperaturaDTO.setNombre(sensorTemperatura.getNombre());
        sensorTemperaturaDTO.setNotificacion(sensorTemperatura.getNotificacion());
        sensorTemperaturaDTO.setDatosTemperatura(sensorTemperatura.getDatosTemperatura());
        sensorTemperaturaDTO.setSensorTemperatura(sensorTemperatura.getSensorTemperatura() == null
                ? null
                : sensorTemperatura.getSensorTemperatura().getId());
        sensorTemperaturaDTO.setToken(sensorTemperatura.getToken());
        return sensorTemperaturaDTO;
    }

    private SensorTemperatura mapToEntity(final SensorTemperaturaDTO sensorTemperaturaDTO,
                                          final SensorTemperatura sensorTemperatura) {
        sensorTemperatura.setNombre(sensorTemperaturaDTO.getNombre());
        sensorTemperatura.setNotificacion(sensorTemperaturaDTO.getNotificacion());
        sensorTemperatura.setDatosTemperatura(sensorTemperaturaDTO.getDatosTemperatura());
        final Usuario usuarioAsociado = sensorTemperaturaDTO.getSensorTemperatura() == null
                ? null
                : usuarioRepository.findById(sensorTemperaturaDTO.getSensorTemperatura())
                .orElseThrow(() -> new NotFoundException("Usuario asociado no encontrado"));
        sensorTemperatura.setSensorTemperatura(usuarioAsociado);
        return sensorTemperatura;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final SensorTemperatura sensorTemperatura = sensorTemperaturaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Evento eventosTemperaturaEvento = eventoRepository.findFirstByEventosTemperatura(sensorTemperatura);
        if (eventosTemperaturaEvento != null) {
            referencedWarning.setKey("sensorTemperatura.evento.eventosTemperatura.referenced");
            referencedWarning.addParam(eventosTemperaturaEvento.getId());
            return referencedWarning;
        }
        return null;
    }

    private String generateToken(String tipo) {
        // Genera un token genérico sin el nombre específico del sensor
        return tipo + "_Sensor";
    }
}
