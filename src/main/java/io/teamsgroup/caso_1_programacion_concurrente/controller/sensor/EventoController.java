package io.teamsgroup.caso_1_programacion_concurrente.controller.sensor;

import io.teamsgroup.caso_1_programacion_concurrente.model.EventoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.model.sensor.EventoRequest;
import io.teamsgroup.caso_1_programacion_concurrente.service.sensor.EventoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/events_1")
    public ResponseEntity<List<EventoDTO>> getAllEventos() {
        List<EventoDTO> eventos = eventoService.getAllEventos();
        return ResponseEntity.ok(eventos);
    }
    @GetMapping("/events_2")
    public ResponseEntity<List<EventoDTO>> getEventoTemperatura() {
        List<EventoDTO> eventos = eventoService.obtenerEventosTemperatura();
        return ResponseEntity.ok(eventos);
    }
}



