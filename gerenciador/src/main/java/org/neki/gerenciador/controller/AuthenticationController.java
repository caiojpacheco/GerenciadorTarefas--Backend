package org.neki.gerenciador.controller;

import java.time.LocalDateTime;

import org.neki.gerenciador.dto.AuthenticationDto;
import org.neki.gerenciador.dto.LoginResponseDto;
import org.neki.gerenciador.dto.RegisterDto;
import org.neki.gerenciador.model.Administrador;
import org.neki.gerenciador.repository.AdministradorRepository;
import org.neki.gerenciador.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {
	@Autowired
	private AdministradorRepository administradorRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@Operation(summary = "Login do administrador", description = "Permite ao administrador cadastrado se logar na plataforma.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Requisição encontrada!"),
			@ApiResponse(responseCode = "404", description = "Busca não encontrada. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	@PostMapping("/login")
	public ResponseEntity<?> logarNovoAdministrador(@RequestBody @Valid AuthenticationDto data) {
		var emailSenha = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getSenha());
	    var auth = this.authenticationManager.authenticate(emailSenha);
	    var administrador = (Administrador) auth.getPrincipal();
	    var token = tokenService.generateToken(administrador);
	    var loginResponse = new LoginResponseDto(
	            token,
	            administrador.getId()
	        );
	    return ResponseEntity.ok(loginResponse);
	}
	
	@Operation(summary = "Registro de novo administrador", description = "Permite que um administrador realize seu cadastro na plataforma.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Requisição encontrada!"),
			@ApiResponse(responseCode = "404", description = "Busca não encontrada. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	@PostMapping("/register")
	public ResponseEntity<?> registrarNovoUsuario(@RequestBody @Valid RegisterDto registerDto) {
		if (this.administradorRepository.findByEmail(registerDto.email()) != null)
			return ResponseEntity.badRequest().build();
		String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.senha());
		Administrador novoAdministrador = new Administrador(registerDto.email(), encryptedPassword,
				registerDto.nome());
		if(!registerDto.senha().equals(registerDto.confirmaSenha())) {
			   throw new Error("Senhas não são iguais");
		}
		novoAdministrador.setDataCadastro(LocalDateTime.now());
		this.administradorRepository.save(novoAdministrador);
		return ResponseEntity.ok().build();
	}
}