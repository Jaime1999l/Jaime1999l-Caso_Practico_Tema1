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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final SensorAccesoRepository sensorAccesoRepository;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5); // 5 hilos para 5 sensores

    private static final Random RANDOM = new Random();

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
        return evento;
    }

    // ========================
    // NUEVO CODIGO [ HILOS ]
    // ========================

    public void iniciarGeneracionEventosConcurrentes() {
        iniciarGeneracionEventosMovimiento();
        iniciarGeneracionEventosTemperatura();
        iniciarGeneracionEventosAcceso();
    }

    // ========================
    // Logica para eventos de Sensores de Movimiento
    // ========================
    private void iniciarGeneracionEventosMovimiento() {
        List<SensorMovimiento> sensoresMovimiento = sensorMovimientoRepository.findAll();
        for (SensorMovimiento sensor : sensoresMovimiento.subList(0, Math.min(sensoresMovimiento.size(), 5))) {
            scheduler.scheduleAtFixedRate(() -> generarEventoMovimiento(sensor),
                    getRandomInterval(), getRandomInterval(), TimeUnit.MINUTES);
        }
    }

    private void generarEventoMovimiento(SensorMovimiento sensor) {
        try {
            Evento evento = new Evento();
            evento.setNombre("Movimiento detectado en sensor " + sensor.getId());
            evento.setDatos(sensor.getDatosMovimiento());
            evento.setDateCreated(OffsetDateTime.now());
            evento.setLastUpdated(OffsetDateTime.now());
            evento.setEventosMovimiento(sensor);
            eventoRepository.save(evento);
            System.out.println("Evento generado (Movimiento): " + evento.getNombre() + " - Sensor ID: " + sensor.getId());
        } catch (Exception e) {
            System.err.println("Error al generar evento de movimiento: " + e.getMessage());
        }
    }

    // ========================
    // Logica para eventos de Sensores de Temperatura
    // ========================
    private void iniciarGeneracionEventosTemperatura() {
        List<SensorTemperatura> sensoresTemperatura = sensorTemperaturaRepository.findAll();
        for (SensorTemperatura sensor : sensoresTemperatura.subList(0, Math.min(sensoresTemperatura.size(), 5))) {
            scheduler.scheduleAtFixedRate(() -> generarEventoTemperatura(sensor),
                    getRandomInterval(), getRandomInterval(), TimeUnit.MINUTES);
        }
    }

    private void generarEventoTemperatura(SensorTemperatura sensor) {
        try {
            Evento evento = new Evento();
            evento.setNombre("Temperatura registrada en sensor " + sensor.getId());
            evento.setDatos(sensor.getDatosTemperatura().toString());
            evento.setDateCreated(OffsetDateTime.now());
            evento.setLastUpdated(OffsetDateTime.now());
            evento.setEventosTemperatura(sensor);
            eventoRepository.save(evento);
            System.out.println("Evento generado (Temperatura): " + evento.getNombre() + " - Sensor ID: " + sensor.getId());
        } catch (Exception e) {
            System.err.println("Error al generar evento de temperatura: " + e.getMessage());
        }
    }

    // ========================
    // Logica para eventos de Sensores de Acceso
    // ========================
    private void iniciarGeneracionEventosAcceso() {
        List<SensorAcceso> sensoresAcceso = sensorAccesoRepository.findAll();
        for (SensorAcceso sensor : sensoresAcceso.subList(0, Math.min(sensoresAcceso.size(), 5))) {
            scheduler.scheduleAtFixedRate(() -> generarEventoAcceso(sensor),
                    getRandomInterval(), getRandomInterval(), TimeUnit.MINUTES);
        }
    }

    private void generarEventoAcceso(SensorAcceso sensor) {
        try {
            Evento evento = new Evento();
            evento.setNombre("Acceso registrado en sensor " + sensor.getId());
            evento.setDatos(sensor.getDatosAcceso() + " - Respuesta: " + (sensor.getRespuesta() ? "Permitido" : "Denegado"));
            evento.setDateCreated(OffsetDateTime.now());
            evento.setLastUpdated(OffsetDateTime.now());
            evento.setEventosAcceso(sensor);
            eventoRepository.save(evento);
            System.out.println("Evento generado (Acceso): " + evento.getNombre() + " - Sensor ID: " + sensor.getId());
        } catch (Exception e) {
            System.err.println("Error al generar evento de acceso: " + e.getMessage());
        }
    }

    // Metodo para obtener un intervalo aleatorio
    private int getRandomInterval() {
        return 1 + RANDOM.nextInt(3);  // Generamos un número entre 1 y 3
    }
}