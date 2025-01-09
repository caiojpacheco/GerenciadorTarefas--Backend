package org.neki.gerenciador.dto;

import java.time.LocalDate;

import org.neki.gerenciador.model.Evento;

public class EventoDto {
	private Long idEvento;
	private String nome;
	private LocalDate data;
	private String localizacao;
	private String imagem;
	private Long idAdm;

	public EventoDto() {
		super();
	}

	public EventoDto(Evento evento) {
		this.idEvento = evento.getIdEvento();
		this.nome = evento.getNome();
		this.data = evento.getData();
		this.localizacao = evento.getLocalizacao();
		this.imagem = evento.getImagem();
		this.idAdm = evento.getAdministrador().getId();
	}

	public EventoDto(Long idEvento, String nome, LocalDate data, String localizacao, String imagem, Long idAdm) {
		this.idEvento = idEvento;
		this.nome = nome;
		this.data = data;
		this.localizacao = localizacao;
		this.imagem = imagem;
		this.idAdm = idAdm;
	}

	public static EventoDto fromEntity(Evento evento) {
		return new EventoDto(evento.getIdEvento(), evento.getNome(), evento.getData(), evento.getLocalizacao(),
				evento.getImagem(), evento.getAdministrador().getId());
	}

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Long getIdAdm() {
		return idAdm;
	}

	public void setIdAdm(Long idAdm) {
		this.idAdm = idAdm;
	}
}
