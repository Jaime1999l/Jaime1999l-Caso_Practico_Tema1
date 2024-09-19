package io.teamsgroup.caso_1_programacion_concurrente.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.teamsgroup.caso_1_programacion_concurrente.model.EventoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/eventos", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventoResource {

    private final EventoService eventoService;

    public EventoResource(final EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<List<EventoDTO>> getAllEventos() {
        return ResponseEntity.ok(eventoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> getEvento(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(eventoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createEvento(@RequestBody @Valid final EventoDTO eventoDTO) {
        final Integer createdId = eventoService.create(eventoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateEvento(@PathVariable(name = "id") final Integer id,
                                                @RequestBody @Valid final EventoDTO eventoDTO) {
        eventoService.update(id, eventoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEvento(@PathVariable(name = "id") final Integer id) {
        eventoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ========================
    // NUEVO ENDPOINT
    // ========================

    @PostMapping("/iniciar-generacion-eventos")
    public ResponseEntity<Void> iniciarGeneracionEventos() {
        eventoService.iniciarGeneracionEventosConcurrentes();
        return ResponseEntity.ok().build();
    }
}

