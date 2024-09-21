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
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SensorMovimientoService {

    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public SensorMovimientoService(final SensorMovimientoRepository sensorMovimientoRepository,
                                   final UsuarioRepository usuarioRepository, final EventoRepository eventoRepository) {
        this.sensorMovimientoRepository = sensorMovimientoRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<SensorMovimientoDTO> findAll() {
        final List<SensorMovimiento> sensorMovimientoes = sensorMovimientoRepository.findAll(Sort.by("id"));
        return sensorMovimientoes.stream()
                .map(sensorMovimiento -> mapToDTO(sensorMovimiento, new SensorMovimientoDTO()))
                .toList();
    }

    public SensorMovimientoDTO get(final Integer id) {
        return sensorMovimientoRepository.findById(id)
                .map(sensorMovimiento -> mapToDTO(sensorMovimiento, new SensorMovimientoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final SensorMovimientoDTO sensorMovimientoDTO) {
        final SensorMovimiento sensorMovimiento = new SensorMovimiento();
        mapToEntity(sensorMovimientoDTO, sensorMovimiento);
        return sensorMovimientoRepository.save(sensorMovimiento).getId();
    }

    public void update(final Integer id, final SensorMovimientoDTO sensorMovimientoDTO) {
        final SensorMovimiento sensorMovimiento = sensorMovimientoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sensorMovimientoDTO, sensorMovimiento);
        sensorMovimientoRepository.save(sensorMovimiento);
    }

    public void delete(final Integer id) {
        sensorMovimientoRepository.deleteById(id);
    }

    /*@Async
    public void processSensorMovimientoEvents() {
        List<SensorMovimiento> sensors = sensorMovimientoRepository.findAll();
        for (SensorMovimiento sensorMovimiento : sensors) {
            processSensorMovimientoEvent(sensorMovimiento);
        }
    }

    private void processSensorMovimientoEvent(SensorMovimiento sensorMovimiento) {
        Evento evento = new Evento();
        evento.setDatos("Evento generado por SensorMovimiento con ID: " + sensorMovimiento.getId());
        eventoRepository.save(evento);
    }*/

    private SensorMovimientoDTO mapToDTO(final SensorMovimiento sensorMovimiento,
                                         final SensorMovimientoDTO sensorMovimientoDTO) {
        sensorMovimientoDTO.setId(sensorMovimiento.getId());
        sensorMovimientoDTO.setNombre(sensorMovimiento.getNombre());
        sensorMovimientoDTO.setNotificacion(sensorMovimiento.getNotificacion());
        sensorMovimientoDTO.setDatosMovimiento(sensorMovimiento.getDatosMovimiento());
        sensorMovimientoDTO.setSensoresMovimiento(sensorMovimiento.getSensoresMovimiento() == null ? null : sensorMovimiento.getSensoresMovimiento().getId());
        return sensorMovimientoDTO;
    }

    private SensorMovimiento mapToEntity(final SensorMovimientoDTO sensorMovimientoDTO,
                                         final SensorMovimiento sensorMovimiento) {
        sensorMovimiento.setNombre(sensorMovimientoDTO.getNombre());
        sensorMovimiento.setNotificacion(sensorMovimientoDTO.getNotificacion());
        sensorMovimiento.setDatosMovimiento(sensorMovimientoDTO.getDatosMovimiento());
        final Usuario sensoresMovimiento = sensorMovimientoDTO.getSensoresMovimiento() == null ? null : usuarioRepository.findById(sensorMovimientoDTO.getSensoresMovimiento())
                .orElseThrow(() -> new NotFoundException("sensoresMovimiento not found"));
        sensorMovimiento.setSensoresMovimiento(sensoresMovimiento);
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

}
