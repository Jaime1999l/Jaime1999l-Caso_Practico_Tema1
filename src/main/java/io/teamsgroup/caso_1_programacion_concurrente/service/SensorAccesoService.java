package io.teamsgroup.caso_1_programacion_concurrente.service;

import io.teamsgroup.caso_1_programacion_concurrente.domain.Evento;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorAcceso;
import io.teamsgroup.caso_1_programacion_concurrente.domain.Usuario;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorAccesoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.repos.EventoRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.SensorAccesoRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.UsuarioRepository;
import io.teamsgroup.caso_1_programacion_concurrente.util.NotFoundException;
import io.teamsgroup.caso_1_programacion_concurrente.util.ReferencedWarning;
import java.util.List;
import org.springframework.scheduling.annotation.Async;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SensorAccesoService {

    private final SensorAccesoRepository sensorAccesoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public SensorAccesoService(final SensorAccesoRepository sensorAccesoRepository,
                               final UsuarioRepository usuarioRepository, final EventoRepository eventoRepository) {
        this.sensorAccesoRepository = sensorAccesoRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<SensorAccesoDTO> findAll() {
        final List<SensorAcceso> sensorAccesoes = sensorAccesoRepository.findAll(Sort.by("id"));
        return sensorAccesoes.stream()
                .map(sensorAcceso -> mapToDTO(sensorAcceso, new SensorAccesoDTO()))
                .toList();
    }

    public SensorAccesoDTO get(final Integer id) {
        return sensorAccesoRepository.findById(id)
                .map(sensorAcceso -> mapToDTO(sensorAcceso, new SensorAccesoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final SensorAccesoDTO sensorAccesoDTO) {
        final SensorAcceso sensorAcceso = new SensorAcceso();
        mapToEntity(sensorAccesoDTO, sensorAcceso);
        return sensorAccesoRepository.save(sensorAcceso).getId();
    }

    public void update(final Integer id, final SensorAccesoDTO sensorAccesoDTO) {
        final SensorAcceso sensorAcceso = sensorAccesoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sensorAccesoDTO, sensorAcceso);
        sensorAccesoRepository.save(sensorAcceso);
    }

    public void delete(final Integer id) {
        sensorAccesoRepository.deleteById(id);
    }

    /*@Async
    public void processSensorAccesoEvents() {
        List<SensorAcceso> sensors = sensorAccesoRepository.findAll();
        for (SensorAcceso sensorAcceso : sensors) {
            processSensorAccesoEvent(sensorAcceso);
        }
    }

    private void processSensorAccesoEvent(SensorAcceso sensorAcceso) {
        Evento evento = new Evento();
        evento.setDatos("Evento generado por SensorAcceso con ID: " + sensorAcceso.getId());
        eventoRepository.save(evento);
    }*/

    private SensorAccesoDTO mapToDTO(final SensorAcceso sensorAcceso,
                                     final SensorAccesoDTO sensorAccesoDTO) {
        sensorAccesoDTO.setId(sensorAcceso.getId());
        sensorAccesoDTO.setNombre(sensorAcceso.getNombre());
        sensorAccesoDTO.setNotificacion(sensorAcceso.getNotificacion());
        sensorAccesoDTO.setDatosAcceso(sensorAcceso.getDatosAcceso());
        sensorAccesoDTO.setRespuesta(sensorAcceso.getRespuesta());
        sensorAccesoDTO.setSensorAcceso(sensorAcceso.getSensorAcceso() == null ? null : sensorAcceso.getSensorAcceso().getId());
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

}