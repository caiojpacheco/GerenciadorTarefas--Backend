package org.neki.gerenciador.controller;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.neki.gerenciador.dto.EventoDto;
import org.neki.gerenciador.dto.EventoRequestDto;
import org.neki.gerenciador.exception.ResourceNotFoundException;
import org.neki.gerenciador.model.Evento;
import org.neki.gerenciador.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/eventos")
public class EventoController {
	
	@Autowired
	private EventoService eventoService;
	
	@Operation(summary = "Criar evento", description = "Permite ao administrador criar um evento com uma data e localização.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Requisição encontrada!"),
			@ApiResponse(responseCode = "404", description = "Busca não encontrada. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	@PostMapping
	public EventoDto salvarEvento(@RequestBody @Valid EventoRequestDto requestDto)
			throws AccessDeniedException {
		return eventoService.criarEvento(requestDto);
	}
	
	@Operation(summary = "Lista eventos", description = "Lista todas os eventos presentes no banco de dados.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Requisição encontrada!"),
			@ApiResponse(responseCode = "404", description = "Busca não encontrada. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	@GetMapping
	public ResponseEntity<List<EventoDto>> filtrarTodos() throws AccessDeniedException {
		return ResponseEntity.ok(eventoService.listarTodosEventos());
	}
	
	@Operation(summary = "Filtra um evento de acordo com o seu ID", description = "Encontra um evento de acordo com o seu ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Requisição encontrada!"),
			@ApiResponse(responseCode = "404", description = "Busca não encontrada. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	@GetMapping("/{id}")
	public ResponseEntity<EventoDto> filtrarPorIdEvento(@PathVariable Long id) throws AccessDeniedException {
		return ResponseEntity.ok(eventoService.obterEvento(id));
	}
	
	@Operation(summary = "Filtra os eventos de acordo com o ID do administrador", description = "Permite buscar todas os eventos relacionados ao ID de um determinado administrador.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Requisição encontrada!"),
			@ApiResponse(responseCode = "404", description = "Busca não encontrada. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	@GetMapping("/adm/{admId}")
	public ResponseEntity<List<EventoDto>> filtrarPorId(@PathVariable Long admId)
			throws AccessDeniedException {
		List<EventoDto> eventos = eventoService.listarEventosDoAdm(admId);
		return ResponseEntity.status(HttpStatus.OK).body(eventos);
	}
	
	@Operation(summary = "Atualizar evento", description = "Permite ao administrador atualizar o evento.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Requisição encontrada!"),
			@ApiResponse(responseCode = "404", description = "Busca não encontrada. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	@PutMapping("/{id}")
	public ResponseEntity<EventoDto> atualizarEvento(@PathVariable Long id,
			@RequestBody EventoRequestDto requestDto) throws AccessDeniedException {
		Evento eventoAtualizado = eventoService.atualizarEvento(id, requestDto);
		EventoDto responseDto = EventoDto.fromEntity(eventoAtualizado);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
	}
	
	@Operation(summary = "Deletar evento", description = "Permite ao administrador deletar um evento.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Requisição encontrada!"),
			@ApiResponse(responseCode = "404", description = "Busca não encontrada. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> apagarEvento(@PathVariable Long id)
			throws ResourceNotFoundException, AccessDeniedException {
		eventoService.deletarEvento(id);
		return ResponseEntity.noContent().build();
	}
}
