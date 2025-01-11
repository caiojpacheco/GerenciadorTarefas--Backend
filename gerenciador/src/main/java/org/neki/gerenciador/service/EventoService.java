package org.neki.gerenciador.service;

import java.time.LocalDateTime;
import java.util.List;

import org.neki.gerenciador.dto.EventoDto;
import org.neki.gerenciador.dto.EventoRequestDto;
import org.neki.gerenciador.exception.ResourceNotFoundException;
import org.neki.gerenciador.model.Administrador;
import org.neki.gerenciador.model.Evento;
import org.neki.gerenciador.repository.AdministradorRepository;
import org.neki.gerenciador.repository.EventoRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final AdministradorRepository administradorRepository;

    public EventoService(EventoRepository eventoRepository, AdministradorRepository administradorRepository) {
        this.eventoRepository = eventoRepository;
        this.administradorRepository = administradorRepository;
    }

    @Transactional
    public EventoDto criarEvento(EventoRequestDto requestDto, String email) {
        Administrador administrador = (Administrador) administradorRepository.findByEmail(email);
        if (administrador == null) {
            throw new ResourceNotFoundException("Administrador não encontrado");
        }

        Evento evento = new Evento();
        evento.setNome(requestDto.nome());
        evento.setData(requestDto.data());
        evento.setLocalizacao(requestDto.localizacao());
        evento.setImagem(requestDto.imagem());
        evento.setAdministrador(administrador);
        evento.setDataInicial(LocalDateTime.now());

        evento = eventoRepository.save(evento);
        return new EventoDto(evento);
    }

    public List<EventoDto> listarTodosEventos() {
        return eventoRepository.findAll().stream()
                .map(EventoDto::fromEntity)
                .toList();
    }

    public EventoDto obterEvento(Long id) {
        return eventoRepository.findById(id)
                .map(EventoDto::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));
    }

    public List<EventoDto> listarEventosDoAdm(Long adminId, String email) {
        Administrador administrador = (Administrador) administradorRepository.findByEmail(email);
        if (administrador == null) {
            throw new ResourceNotFoundException("Administrador não encontrado");
        }

        if (!administrador.getId().equals(adminId)) {
            throw new AccessDeniedException("Não autorizado a ver eventos de outro administrador");
        }

        return eventoRepository.findByAdmId(adminId).stream()
                .map(EventoDto::fromEntity)
                .toList();
    }

    @Transactional
    public EventoDto atualizarEvento(Long id, EventoRequestDto requestDto, String email) {
        Administrador administrador = (Administrador) administradorRepository.findByEmail(email);
        if (administrador == null) {
            throw new ResourceNotFoundException("Administrador não encontrado");
        }

        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));

        if (!evento.getAdministrador().getId().equals(administrador.getId())) {
            throw new AccessDeniedException("Não autorizado a atualizar este evento");
        }

        evento.setNome(requestDto.nome());
        evento.setData(requestDto.data());
        evento.setLocalizacao(requestDto.localizacao());
        evento.setImagem(requestDto.imagem());
        evento.setDataDeAlteracao(LocalDateTime.now());

        evento = eventoRepository.save(evento);
        return new EventoDto(evento);
    }

    @Transactional
    public void deletarEvento(Long id, String email) {
        Administrador administrador = (Administrador) administradorRepository.findByEmail(email);
        if (administrador == null) {
            throw new ResourceNotFoundException("Administrador não encontrado");
        }

        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));

        if (!evento.getAdministrador().getId().equals(administrador.getId())) {
            throw new AccessDeniedException("Não autorizado a deletar este evento");
        }

        eventoRepository.delete(evento);
    }
}