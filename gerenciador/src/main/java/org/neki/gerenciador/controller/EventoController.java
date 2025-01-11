package org.neki.gerenciador.controller;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.neki.gerenciador.dto.EventoDto;
import org.neki.gerenciador.dto.EventoRequestDto;
import org.neki.gerenciador.exception.ResourceNotFoundException;
import org.neki.gerenciador.service.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/eventos")
public class EventoController {
    
    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping
    @Operation(summary = "Criar evento", description = "Permite ao administrador criar um evento com uma data e localização.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EventoDto> salvarEvento(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid EventoRequestDto requestDto) throws AccessDeniedException {
        EventoDto evento = eventoService.criarEvento(requestDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(evento);
    }

    @GetMapping
    @Operation(summary = "Listar eventos", description = "Lista todos os eventos disponíveis")
    public ResponseEntity<List<EventoDto>> listarTodos() {
        return ResponseEntity.ok(eventoService.listarTodosEventos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar evento por ID", description = "Retorna um evento específico pelo ID")
    public ResponseEntity<EventoDto> buscarPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(eventoService.obterEvento(id));
    }

    @GetMapping("/admin/{adminId}")
    @Operation(summary = "Listar eventos por administrador", description = "Lista todos os eventos de um administrador específico")
    public ResponseEntity<List<EventoDto>> listarPorAdministrador(
            @PathVariable Long adminId,
            @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException {
        List<EventoDto> eventos = eventoService.listarEventosDoAdm(adminId, userDetails.getUsername());
        return ResponseEntity.ok(eventos);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar evento", description = "Atualiza um evento existente")
    public ResponseEntity<EventoDto> atualizarEvento(
            @PathVariable Long id,
            @RequestBody @Valid EventoRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException {
        EventoDto eventoAtualizado = eventoService.atualizarEvento(id, requestDto, userDetails.getUsername());
        return ResponseEntity.ok(eventoAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar evento", description = "Remove um evento existente")
    public ResponseEntity<Void> deletarEvento(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException {
        eventoService.deletarEvento(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}