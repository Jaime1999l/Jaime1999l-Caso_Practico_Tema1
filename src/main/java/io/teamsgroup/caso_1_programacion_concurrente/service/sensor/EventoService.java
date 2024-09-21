package io.teamsgroup.caso_1_programacion_concurrente.service.sensor;

import io.teamsgroup.caso_1_programacion_concurrente.domain.Evento;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorMovimiento;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorAcceso;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorTemperatura;
import io.teamsgroup.caso_1_programacion_concurrente.model.EventoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.repos.EventoRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.SensorMovimientoRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.SensorAccesoRepository;
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
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

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

    public List<EventoDTO> findAll(String token) {
        List<Evento> eventos = eventoRepository.findAll(Sort.by("id"));

        // Filtrar los sensores que tienen el token proporcionado
        List<Evento> eventos1 = eventos.stream()
                .filter(sensor -> sensor.getToken().equals(token))
                .toList();

        // Si no hay sensores con el token, lanzar excepción
        if (eventos1.isEmpty()) {
            System.out.println("Token inválido para eventos.");
        }

        // Convertir los sensores filtrados a DTO
        return eventos1.stream()
                .map(sensorAcceso -> mapToDTO(sensorAcceso, new EventoDTO()))
                .toList();
    }


    public EventoDTO get(final Integer id) {
        return eventoRepository.findById(id)
                .map(evento -> mapToDTO(evento, new EventoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(EventoDTO eventoDTO) {
        // Crear un nuevo Evento
        Evento evento = new Evento();
        evento.setNombre(eventoDTO.getNombre());
        evento.setDatos(eventoDTO.getDatos());

        // Asociar el evento con un sensor específico (si aplica)
        if (eventoDTO.getEventosMovimiento() != null) {
            SensorMovimiento sensorMovimiento = sensorMovimientoRepository.findById(eventoDTO.getEventosMovimiento())
                    .orElseThrow(() -> new NotFoundException("Sensor de movimiento no encontrado"));
            evento.setEventosMovimiento(sensorMovimiento);
        }

        if (eventoDTO.getEventosAcceso() != null) {
            SensorAcceso sensorAcceso = sensorAccesoRepository.findById(eventoDTO.getEventosAcceso())
                    .orElseThrow(() -> new NotFoundException("Sensor de acceso no encontrado"));
            evento.setEventosAcceso(sensorAcceso);
        }

        if (eventoDTO.getEventosTemperatura() != null) {
            SensorTemperatura sensorTemperatura = sensorTemperaturaRepository.findById(eventoDTO.getEventosTemperatura())
                    .orElseThrow(() -> new NotFoundException("Sensor de temperatura no encontrado"));
            evento.setEventosTemperatura(sensorTemperatura);
        }

        // Generar un token único para este evento
        String token = "TOKEN_EVENTO_" + System.currentTimeMillis() + "_" + eventoDTO.getNombre();
        evento.setToken(token);

        // Guardar el evento en la base de datos
        Evento savedEvento = eventoRepository.save(evento);
        return savedEvento.getId(); // Retornar el ID del evento creado
    }


    public void update(final Integer id, final EventoDTO eventoDTO) {

        final Evento evento = eventoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventoDTO, evento);
        evento.setToken(generateUniqueToken("ACCESO", id));
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
        eventoDTO.setToken(evento.getToken());
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

    public void iniciarGeneracionEventosConcurrentes() {
        System.out.println("Iniciando generación de eventos concurrentes...");

        if (sensorMovimientoRepository.findAll().isEmpty()) {
            System.err.println("Error: No hay sensores de movimiento en la base de datos.");
        }
        if (sensorTemperaturaRepository.findAll().isEmpty()) {
            System.err.println("Error: No hay sensores de temperatura en la base de datos.");
        }
        if (sensorAccesoRepository.findAll().isEmpty()) {
            System.err.println("Error: No hay sensores de acceso en la base de datos.");
        }

        iniciarGeneracionEventosMovimiento();
        iniciarGeneracionEventosTemperatura();
        iniciarGeneracionEventosAcceso();

        System.out.println("Generación de eventos concurrentes iniciada.");
    }

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

            // Generar un token único para el evento
            String token = generateUniqueToken("MOVIMIENTO", sensor.getId());
            evento.setToken(token);

            eventoRepository.save(evento);
            System.out.println("Evento generado (Movimiento): " + evento.getNombre() + " - Sensor ID: " + sensor.getId());
            System.out.println("Token generado para evento de movimiento: " + evento.getToken());
        } catch (Exception e) {
            System.err.println("Error al generar evento de movimiento: " + e.getMessage());
        }
    }

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

            // Generar un token único para el evento
            String token = generateUniqueToken("TEMPERATURA", sensor.getId());
            evento.setToken(token);

            eventoRepository.save(evento);
            System.out.println("Evento generado (Temperatura): " + evento.getNombre() + " - Sensor ID: " + sensor.getId());
            System.out.println("Token generado para evento de temperatura: " + evento.getToken());
        } catch (Exception e) {
            System.err.println("Error al generar evento de temperatura: " + e.getMessage());
        }
    }

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

            // Generar un token único para el evento
            String token = generateUniqueToken("ACCESO", sensor.getId());
            evento.setToken(token);

            eventoRepository.save(evento);
            System.out.println("Evento generado (Acceso): " + evento.getNombre() + " - Sensor ID: " + sensor.getId());
            System.out.println("Token generado para evento de acceso: " + evento.getToken());
        } catch (Exception e) {
            System.err.println("Error al generar evento de acceso: " + e.getMessage());
        }
    }

    private String generateUniqueToken(String tipo, int sensorId) {
        return tipo + "_Sensor_" + sensorId + "_" + System.currentTimeMillis();
    }

    public void deleteAll() {
        eventoRepository.deleteAll();
        System.out.println("Todos los eventos han sido eliminados.");
    }

    public List<EventoDTO> getAllEventos() {
        return eventoRepository.findAll(Sort.by("id")).stream()
                .map(evento -> mapToDTO(evento, new EventoDTO()))
                .toList();
    }

    private int getRandomInterval() {
        return 1 + RANDOM.nextInt(3);  // Generamos un número entre 1 y 3
    }

    public void detenerGeneracionEventos() {
        scheduler.shutdown();
    }
    public List<EventoDTO> obtenerEventosTemperatura() {
        List<Evento> eventos = eventoRepository.findAll();
        List<Evento> eventosTemperatura = eventos.stream()
                .filter(evento -> evento.getEventosTemperatura() != null)
                .toList();
        return eventosTemperatura.stream()
                .map(evento -> mapToDTO(evento, new EventoDTO()))
                .toList();
    }
}


