package org.neki.gerenciador.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(request -> {
			var corsConfig = new org.springframework.web.cors.CorsConfiguration();
			corsConfig.setAllowedOriginPatterns(List.of("*"));
			corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
			corsConfig.setAllowedHeaders(List.of("*"));
			corsConfig.setAllowCredentials(true);
			return corsConfig;
		})).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, "/auth/login")
						.permitAll().requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
						
						.requestMatchers(HttpMethod.POST, "/auth/admin/register").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/usuarios/listartodos")
						.hasAnyRole("USERMENTORADO", "USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/usuarios/{idUsuario}")
						.hasAnyRole("USERMENTOR", "USERMENTORADO", "ADMIN")
						.requestMatchers(HttpMethod.PUT, "/usuarios/{idUsuario}")
						.hasAnyRole("USERMENTOR", "USERMENTORADO", "ADMIN")
						.requestMatchers(HttpMethod.PUT, "/usuarios/admin/{idUsuario}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/usuarios/admin/{idUsuario}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/categoria").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/categoria")
						.hasAnyRole("USERMENTOR", "USERMENTORADO", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/categoria/{id}")
						.hasAnyRole("USERMENTOR", "USERMENTORADO", "ADMIN")
						.requestMatchers(HttpMethod.PUT, "/categoria/{id}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/categoria/{id}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/agendas").hasAnyRole("USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.POST, "/agendas/lista").hasAnyRole("USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/agendas").hasAnyRole("USERMENTORADO", "USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/agendas/{id}").hasAnyRole("USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/agendas/mentor/{mentorId}")
						.hasAnyRole("USERMENTORADO", "USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/agendas/disponiveis")
						.hasAnyRole("USERMENTORADO", "USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/agendas/categorias/{categoriaId}/disponiveis")
						.hasAnyRole("USERMENTORADO", "USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/agendas/mentor/{mentorId}/disponiveis")
						.hasAnyRole("USERMENTORADO", "USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.PUT, "/agendas/{id}").hasAnyRole("USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/agendas/{id}").hasAnyRole("USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.POST, "/reservas").hasAnyRole("USERMENTORADO", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/reservas").hasAnyRole("USERMENTOR", "USERMENTORADO", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/reservas/{id}")
						.hasAnyRole("USERMENTORADO", "USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/reservas/agendas-disponiveis/{mentorId}")
						.hasAnyRole("USERMENTORADO", "USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.PUT, "/reservas/{reservaId}/reagendar/{novaAgendaId}")
						.hasAnyRole("USERMENTORADO", "ADMIN")
						.requestMatchers(HttpMethod.PUT, "/reservas/cancelar/{reservaId}")
						.hasAnyRole("USERMENTOR", "USERMENTORADO", "ADMIN")
						.requestMatchers(HttpMethod.POST, "/mentorias")
						.hasAnyRole("USERMENTORADO", "USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/mentorias").hasAnyRole("USERMENTOR", "USERMENTORADO", "ADMIN")  //.hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/mentorias/mentor/{mentorId}")
						.hasAnyRole("USERMENTOR", "USERMENTORADO", "ADMIN") //.hasAnyRole("USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/mentorias/mentorado/{mentoradoId}")
						.hasAnyRole("USERMENTORADO", "USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/mentorias/historico/mentorado/{mentoradoId}")
						.hasAnyRole("USERMENTOR", "USERMENTORADO", "ADMIN") //.hasAnyRole("USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/historico/{mentorId}/{mentoradoId}")
						.hasAnyRole("USERMENTORADO", "USERMENTOR", "ADMIN")
						.requestMatchers(HttpMethod.PUT, "/mentorias/mentoria/{idMentoria}").hasAnyRole("USERMENTOR", "USERMENTORADO", "ADMIN")  //hasAnyRole("USERMENTOR", "ADMIN") 
						.requestMatchers(HttpMethod.DELETE, "/mentorias/admin/{id}").hasAnyRole("USERMENTOR", "USERMENTORADO", "ADMIN")  //hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/admin/listar-reservas").hasAnyRole("USERMENTOR", "USERMENTORADO", "ADMIN")  // hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/admin/total-mentoriasfinalizadas").hasAnyRole("USERMENTOR", "USERMENTORADO", "ADMIN")  //hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/admin/listar-usuarios").hasAnyRole("USERMENTOR", "USERMENTORADO", "ADMIN")  //.hasRole("ADMIN")
						.anyRequest()
						.authenticated())
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}