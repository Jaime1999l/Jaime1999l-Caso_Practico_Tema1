package io.teamsgroup.caso_1_programacion_concurrente.service.sensor;

import io.teamsgroup.caso_1_programacion_concurrente.domain.Evento;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorAcceso;
import io.teamsgroup.caso_1_programacion_concurrente.domain.Usuario;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorAccesoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.repos.EventoRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.SensorAccesoRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.UsuarioRepository;
import io.teamsgroup.caso_1_programacion_concurrente.util.NotFoundException;
import io.teamsgroup.caso_1_programacion_concurrente.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorAccesoService {

    private final SensorAccesoRepository sensorAccesoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public SensorAccesoService(final SensorAccesoRepository sensorAccesoRepository,
                               final UsuarioRepository usuarioRepository,
                               final EventoRepository eventoRepository) {
        this.sensorAccesoRepository = sensorAccesoRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<SensorAccesoDTO> findAll() {
        final List<SensorAcceso> sensorAccesos = sensorAccesoRepository.findAll(Sort.by("id"));
        return sensorAccesos.stream()
                .map(sensorAcceso -> mapToDTO(sensorAcceso, new SensorAccesoDTO()))
                .toList();
    }

    public SensorAccesoDTO get(final Integer id) {
        return sensorAccesoRepository.findById(id)
                .map(sensorAcceso -> mapToDTO(sensorAcceso, new SensorAccesoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final SensorAccesoDTO sensorAccesoDTO) {
        SensorAcceso sensorAcceso = new SensorAcceso();
        sensorAcceso.setNombre(sensorAccesoDTO.getNombre());
        sensorAcceso.setDatosAcceso(sensorAccesoDTO.getDatosAcceso());
        sensorAcceso.setNotificacion(sensorAccesoDTO.getNotificacion());
        sensorAcceso.setRespuesta(sensorAccesoDTO.getRespuesta());
        sensorAcceso.setToken("TOKEN_ACCESO_" + sensorAccesoDTO.getNombre()); // Genera un token único
        System.out.println("Token generado para Sensor de Acceso: " + sensorAcceso.getToken()); // Muestra el token en consola
        return sensorAccesoRepository.save(sensorAcceso).getId();
    }



    public void update(final Integer id, final SensorAccesoDTO sensorAccesoDTO) {
        final SensorAcceso sensorAcceso = sensorAccesoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sensorAccesoDTO, sensorAcceso);
        sensorAcceso.setToken(generateToken("ACCESO", sensorAcceso.getNombre()));
        sensorAccesoRepository.save(sensorAcceso);
    }

    public void delete(final Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser null");
        }
        sensorAccesoRepository.deleteById(id);
    }

    private SensorAccesoDTO mapToDTO(final SensorAcceso sensorAcceso,
                                     final SensorAccesoDTO sensorAccesoDTO) {
        sensorAccesoDTO.setId(sensorAcceso.getId());
        sensorAccesoDTO.setNombre(sensorAcceso.getNombre());
        sensorAccesoDTO.setNotificacion(sensorAcceso.getNotificacion());
        sensorAccesoDTO.setDatosAcceso(sensorAcceso.getDatosAcceso());
        sensorAccesoDTO.setRespuesta(sensorAcceso.getRespuesta());
        sensorAccesoDTO.setSensorAcceso(sensorAcceso.getSensorAcceso() == null ? null : sensorAcceso.getSensorAcceso().getId());
        sensorAccesoDTO.setToken(sensorAcceso.getToken());
        return sensorAccesoDTO;
    }

    private SensorAcceso mapToEntity(final SensorAccesoDTO sensorAccesoDTO,
                                     final SensorAcceso sensorAcceso) {
        sensorAcceso.setNombre(sensorAccesoDTO.getNombre());
        sensorAcceso.setNotificacion(sensorAccesoDTO.getNotificacion());
        sensorAcceso.setDatosAcceso(sensorAccesoDTO.getDatosAcceso());
        sensorAcceso.setRespuesta(sensorAccesoDTO.getRespuesta());
        final Usuario usuarioAsociado = sensorAccesoDTO.getSensorAcceso() == null
                ? null
                : usuarioRepository.findById(sensorAccesoDTO.getSensorAcceso())
                .orElseThrow(() -> new NotFoundException("Usuario asociado no encontrado"));

        sensorAcceso.setSensorAcceso(usuarioAsociado);

        return sensorAcceso;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final SensorAcceso sensorAcceso = sensorAccesoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Evento eventosAccesoEvento = eventoRepository.findFirstByEventosAcceso(sensorAcceso);
        if (eventosAccesoEvento != null) {
            referencedWarning.setKey("sensorAcceso.evento.eventosAcceso.referenced");
            referencedWarning.addParam(eventosAccesoEvento.getId());
            return referencedWarning;
        }
        return null;
    }

    // Generador simple de tokens basados en el tipo de sensor y su nombre
    private String generateToken(String tipo, String nombre) {
        return tipo + "_" + nombre + "_" + System.currentTimeMillis();
    }
}
