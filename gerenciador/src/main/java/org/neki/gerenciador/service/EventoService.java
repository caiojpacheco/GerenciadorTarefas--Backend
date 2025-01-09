package org.neki.gerenciador.service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

import org.neki.gerenciador.dto.EventoDto;
import org.neki.gerenciador.dto.EventoRequestDto;
import org.neki.gerenciador.exception.ResourceNotFoundException;
import org.neki.gerenciador.model.Administrador;
import org.neki.gerenciador.model.Evento;
import org.neki.gerenciador.repository.AdministradorRepository;
import org.neki.gerenciador.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;


@Service
public class EventoService {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private AdministradorRepository administradorRepository;
	
	public EventoDto criarEvento(EventoRequestDto requestDto) throws AccessDeniedException {
		Administrador administrador = administradorRepository.findById(requestDto.idAdm())
                .orElseThrow(() -> new ResourceNotFoundException("Administrador não encontrado"));
		Evento evento = new Evento();
		evento.setNome(requestDto.nome());
		evento.setData(requestDto.data());
		evento.setLocalizacao(requestDto.localizacao());
		evento.setImagem(requestDto.imagem());
		evento.setAdministrador(administrador);
		evento.setDataInicial(LocalDateTime.now());
		eventoRepository.save(evento);
		EventoDto eventoDto = new EventoDto(evento);
		return eventoDto;
	}
	
	public List<EventoDto> listarTodosEventos() throws AccessDeniedException {
		return eventoRepository.findAll().stream().map(EventoDto::fromEntity).toList();
	}
	
	public List<EventoDto> listarEventosDoAdm(Long admId) throws AccessDeniedException {
		String currentUserEmail = getCurrentUserEmail();
		Administrador administrador = (Administrador) administradorRepository.findByEmail(currentUserEmail);
		if (administrador == null) {
			throw new UsernameNotFoundException("Administrador não encontrado: " + currentUserEmail);
		}
		return eventoRepository.findByAdmId(admId).stream().map(EventoDto::fromEntity)
				.toList();
	}
	
	public EventoDto obterEvento(Long idEvento) throws AccessDeniedException {
		Evento evento = eventoRepository.findById(idEvento)
				.orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + idEvento));

		return EventoDto.fromEntity(evento);
	}
	
	@Transactional
	public Evento atualizarEvento(Long id, EventoRequestDto requestDto) throws AccessDeniedException {
		String currentUserEmail = getCurrentUserEmail();
		Administrador administrador = (Administrador) administradorRepository.findByEmail(currentUserEmail);
		if (administrador == null) {
			throw new UsernameNotFoundException("Administrador não encontrado: " + currentUserEmail);
		}

		Evento evento = eventoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado."));

		evento.setNome(requestDto.nome());
		evento.setData(requestDto.data());
		evento.setLocalizacao(requestDto.localizacao());
		evento.setImagem(requestDto.imagem());
		evento.setAdministrador(administrador);
		evento.setDataDeAlteracao(LocalDateTime.now());

		return eventoRepository.save(evento);
	}
	
	@Transactional
	public boolean deletarEvento(Long id) {
        if(!eventoRepository.existsById(id)) {
            return false;
        }
        eventoRepository.deleteById(id);
        return true;
    }
	
	public String getCurrentUserEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}
