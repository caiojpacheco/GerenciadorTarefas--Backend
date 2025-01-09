package org.neki.gerenciador.dto;

public record RegisterDto(
		String email,
		String senha,
		String confirmaSenha,
		String nome) {
}
