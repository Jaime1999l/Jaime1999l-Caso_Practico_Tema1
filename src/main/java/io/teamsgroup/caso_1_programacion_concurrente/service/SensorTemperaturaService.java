package io.teamsgroup.caso_1_programacion_concurrente.service;

import io.teamsgroup.caso_1_programacion_concurrente.domain.Evento;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorTemperatura;
import io.teamsgroup.caso_1_programacion_concurrente.domain.Usuario;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorTemperaturaDTO;
import io.teamsgroup.caso_1_programacion_concurrente.repos.EventoRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.SensorTemperaturaRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.UsuarioRepository;
import io.teamsgroup.caso_1_programacion_concurrente.util.NotFoundException;
import io.teamsgroup.caso_1_programacion_concurrente.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SensorTemperaturaService {

    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public SensorTemperaturaService(final SensorTemperaturaRepository sensorTemperaturaRepository,
            final UsuarioRepository usuarioRepository, final EventoRepository eventoRepository) {
        this.sensorTemperaturaRepository = sensorTemperaturaRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<SensorTemperaturaDTO> findAll() {
        final List<SensorTemperatura> sensorTemperaturas = sensorTemperaturaRepository.findAll(Sort.by("id"));
        return sensorTemperaturas.stream()
                .map(sensorTemperatura -> mapToDTO(sensorTemperatura, new SensorTemperaturaDTO()))
                .toList();
    }

    public SensorTemperaturaDTO get(final Integer id) {
        return sensorTemperaturaRepository.findById(id)
                .map(sensorTemperatura -> mapToDTO(sensorTemperatura, new SensorTemperaturaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final SensorTemperaturaDTO sensorTemperaturaDTO) {
        final SensorTemperatura sensorTemperatura = new SensorTemperatura();
        mapToEntity(sensorTemperaturaDTO, sensorTemperatura);
        return sensorTemperaturaRepository.save(sensorTemperatura).getId();
    }

    public void update(final Integer id, final SensorTemperaturaDTO sensorTemperaturaDTO) {
        final SensorTemperatura sensorTemperatura = sensorTemperaturaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sensorTemperaturaDTO, sensorTemperatura);
        sensorTemperaturaRepository.save(sensorTemperatura);
    }

    public void delete(final Integer id) {
        sensorTemperaturaRepository.deleteById(id);
    }

    private SensorTemperaturaDTO mapToDTO(final SensorTemperatura sensorTemperatura,
            final SensorTemperaturaDTO sensorTemperaturaDTO) {
        sensorTemperaturaDTO.setId(sensorTemperatura.getId());
        sensorTemperaturaDTO.setNombre(sensorTemperatura.getNombre());
        sensorTemperaturaDTO.setNotificacion(sensorTemperatura.getNotificacion());
        sensorTemperaturaDTO.setDatosTemperatura(sensorTemperatura.getDatosTemperatura());
        sensorTemperaturaDTO.setSensorTemperatura(sensorTemperatura.getSensorTemperatura() == null ? null : sensorTemperatura.getSensorTemperatura().getId());
        return sensorTemperaturaDTO;
    }

    private SensorTemperatura mapToEntity(final SensorTemperaturaDTO sensorTemperaturaDTO,
            final SensorTemperatura sensorTemperatura) {
        sensorTemperatura.setNombre(sensorTemperaturaDTO.getNombre());
        sensorTemperatura.setNotificacion(sensorTemperaturaDTO.getNotificacion());
        sensorTemperatura.setDatosTemperatura(sensorTemperaturaDTO.getDatosTemperatura());
        final Usuario sensorTemperatura = sensorTemperaturaDTO.getSensorTemperatura() == null ? null : usuarioRepository.findById(sensorTemperaturaDTO.getSensorTemperatura())
                .orElseThrow(() -> new NotFoundException("sensorTemperatura not found"));
        sensorTemperatura.setSensorTemperatura(sensorTemperatura);
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

}
