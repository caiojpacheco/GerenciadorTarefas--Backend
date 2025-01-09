package org.neki.gerenciador.dto;

import org.neki.gerenciador.model.Administrador;

public class AdministradorDto {

	private Long id;
	private String email;
	private String nome;
	
	public AdministradorDto() {
		super();
	}

	public AdministradorDto(Administrador administrador) {
		this.id = administrador.getId();
		this.email = administrador.getEmail();
		this.nome = administrador.getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
