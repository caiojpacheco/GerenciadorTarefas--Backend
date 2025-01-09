package org.neki.gerenciador.repository;

import java.util.List;

import org.neki.gerenciador.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
	
	@Query("SELECT e FROM Evento e WHERE e.adm.id = :admId")
	List<Evento> findByAdmId(@Param("admId") Long admId);
}
