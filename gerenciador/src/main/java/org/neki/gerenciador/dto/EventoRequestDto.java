package org.neki.gerenciador.dto;

import java.time.LocalDate;

public record EventoRequestDto(
		String nome,
	    LocalDate data,
	    String localizacao,
	    String imagem,
	    Long idAdm
		) {

}
