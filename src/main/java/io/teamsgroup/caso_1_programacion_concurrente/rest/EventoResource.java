package io.teamsgroup.caso_1_programacion_concurrente.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.teamsgroup.caso_1_programacion_concurrente.model.EventoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.model.sensor.EventoRequest;
import io.teamsgroup.caso_1_programacion_concurrente.service.sensor.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/eventos")
public class EventoResource {

    private final EventoService eventoService;

    public EventoResource(final EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping("/events")
    @ApiResponse(responseCode = "200", description = "Get all events")
    public ResponseEntity<List<EventoDTO>> getAllEventos(@RequestBody EventoRequest request) {
        // Pasar el token proporcionado en el cuerpo de la solicitud al método findAll
        return ResponseEntity.ok(eventoService.findAll(request.getToken()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> getEvento(@PathVariable final Integer id, @RequestHeader("Authorization") String token) {
        EventoDTO evento = eventoService.get(id);
        return ResponseEntity.ok(evento);
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createEvento(@RequestBody final EventoDTO eventoDTO, @RequestHeader("Authorization") String token) {
        Integer createdId = eventoService.create(eventoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEvento(@PathVariable final Integer id,
                                             @RequestBody final EventoDTO eventoDTO,
                                             @RequestHeader("Authorization") String token) {
        eventoService.update(id, eventoDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEvento(@PathVariable final Integer id, @RequestHeader("Authorization") String token) {
        eventoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}



