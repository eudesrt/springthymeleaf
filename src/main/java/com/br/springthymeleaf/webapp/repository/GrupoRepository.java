package com.br.springthymeleaf.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.springthymeleaf.webapp.entity.Grupo;
import com.br.springthymeleaf.webapp.entity.Usuario;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
	
	List<Grupo> findByUsuariosIn(Usuario usuarioEntity);

}
