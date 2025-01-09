package org.neki.gerenciador.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Table(name = "admin")
@Entity(name = "admin")
public class Administrador implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_admin")
	private Long id;

	@NotBlank(message = "E-mail não pode ser vazio.")
	@Email(message = "E-mail deve conter um endereço de email válido")
	@Size(max = 100, message = "Email não pode exceder 100 caracteres")
	@Column(name = "email", nullable = false, unique = true, length = 100)
	private String email;

	@NotBlank(message = "A senha não pode ser vazia.")
	@Size(min = 8, max = 255, message = "A senha deve ter no mínimo 8 caracteres.")
	@Column(name = "senha", nullable = false)
	private String senha;

	@NotBlank(message = "O nome não pode ser vazio.")
	@Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres.")
	@Column(name = "nome", nullable = false, length = 50)
	private String nome;

	@OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
	private List<Evento> eventos;

	@Column(name = "admin_dt_cadastro", columnDefinition = "TIMESTAMP")
	private LocalDateTime dataCadastro;

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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Administrador(Long id, String email, String senha, String nome, List<Evento> eventos,
			LocalDateTime dataCadastro) {
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.nome = nome;
		this.eventos = eventos;
		this.dataCadastro = dataCadastro;
	}

	public Administrador() {
	}

	public Administrador(String email, String senha, String nome) {
		this.email = email;
		this.senha = senha;
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Administrador [id=" + id + ", email=" + email + ", senha=" + senha + ", nome=" + nome + ", eventos="
				+ eventos + ", dataCadastro=" + dataCadastro + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public String getPassword() {
		return senha;
	}

}
