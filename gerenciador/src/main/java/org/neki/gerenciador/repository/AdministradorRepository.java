package org.neki.gerenciador.repository;

import java.util.Optional;

import org.neki.gerenciador.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
	Optional<Administrador> findOptionalByEmail(String email);
	
	UserDetails findByEmail(String email);
}
