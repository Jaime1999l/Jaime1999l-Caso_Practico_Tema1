package io.teamsgroup.caso_1_programacion_concurrente.service;

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
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final SensorAccesoRepository sensorAccesoRepository;

    public EventoService(final EventoRepository eventoRepository,
            final SensorMovimientoRepository sensorMovimientoRepository,
            final SensorTemperaturaRepository sensorTemperaturaRepository,
            final SensorAccesoRepository sensorAccesoRepository) {
        this.eventoRepository = eventoRepository;
        this.sensorMovimientoRepository = sensorMovimientoRepository;
        this.sensorTemperaturaRepository = sensorTemperaturaRepository;
        this.sensorAccesoRepository = sensorAccesoRepository;
    }

    public List<EventoDTO> findAll() {
        final List<Evento> eventoes = eventoRepository.findAll(Sort.by("id"));
        return eventoes.stream()
                .map(evento -> mapToDTO(evento, new EventoDTO()))
                .toList();
    }

    public EventoDTO get(final Integer id) {
        return eventoRepository.findById(id)
                .map(evento -> mapToDTO(evento, new EventoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final EventoDTO eventoDTO) {
        final Evento evento = new Evento();
        mapToEntity(eventoDTO, evento);
        return eventoRepository.save(evento).getId();
    }

    public void update(final Integer id, final EventoDTO eventoDTO) {
        final Evento evento = eventoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventoDTO, evento);
        eventoRepository.save(evento);
    }

    public void delete(final Integer id) {
        eventoRepository.deleteById(id);
    }

    private EventoDTO mapToDTO(final Evento evento, final EventoDTO eventoDTO) {
        eventoDTO.setId(evento.getId());
        eventoDTO.setNombre(evento.getNombre());
        eventoDTO.setDatos(evento.getDatos());
        eventoDTO.setEventosMovimiento(evento.getEventosMovimiento() == null ? null : evento.getEventosMovimiento().getId());
        eventoDTO.setEventosTemperatura(evento.getEventosTemperatura() == null ? null : evento.getEventosTemperatura().getId());
        eventoDTO.setEventosAcceso(evento.getEventosAcceso() == null ? null : evento.getEventosAcceso().getId());
        return eventoDTO;
    }

    private Evento mapToEntity(final EventoDTO eventoDTO, final Evento evento) {
        evento.setNombre(eventoDTO.getNombre());
        evento.setDatos(eventoDTO.getDatos());
        final SensorMovimiento eventosMovimiento = eventoDTO.getEventosMovimiento() == null ? null : sensorMovimientoRepository.findById(eventoDTO.getEventosMovimiento())
                .orElseThrow(() -> new NotFoundException("eventosMovimiento not found"));
        evento.setEventosMovimiento(eventosMovimiento);
        final SensorTemperatura eventosTemperatura = eventoDTO.getEventosTemperatura() == null ? null : sensorTemperaturaRepository.findById(eventoDTO.getEventosTemperatura())
                .orElseThrow(() -> new NotFoundException("eventosTemperatura not found"));
        evento.setEventosTemperatura(eventosTemperatura);
        final SensorAcceso eventosAcceso = eventoDTO.getEventosAcceso() == null ? null : sensorAccesoRepository.findById(eventoDTO.getEventosAcceso())
                .orElseThrow(() -> new NotFoundException("eventosAcceso not found"));
        evento.setEventosAcceso(eventosAcceso);
        return evento;
    }

}
