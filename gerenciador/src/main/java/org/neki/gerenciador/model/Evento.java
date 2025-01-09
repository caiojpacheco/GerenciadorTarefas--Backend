package org.neki.gerenciador.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "evento")
public class Evento {
	
	@Id
	@Column(name = "id_evento")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEvento;
	
	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "data", nullable = false)
	private LocalDate data;
	
	@Column(name = "localizacao", nullable = false)
	private String localizacao;
	
	@Column(name = "imagem", nullable = false)
	private String imagem;
	
	@JoinColumn(name = "id_admin")
	@ManyToOne
	private Administrador administrador;
	
	@Column(name = "evento_dt_criacao", columnDefinition = "TIMESTAMP")
	private LocalDateTime DataInicial;

	@Column(name = "evento_dt_alteracao", columnDefinition = "TIMESTAMP")
	private LocalDateTime DataDeAlteracao;

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

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

	public LocalDateTime getDataInicial() {
		return DataInicial;
	}

	public void setDataInicial(LocalDateTime dataInicial) {
		DataInicial = dataInicial;
	}

	public LocalDateTime getDataDeAlteracao() {
		return DataDeAlteracao;
	}

	public void setDataDeAlteracao(LocalDateTime dataDeAlteracao) {
		DataDeAlteracao = dataDeAlteracao;
	}
}
