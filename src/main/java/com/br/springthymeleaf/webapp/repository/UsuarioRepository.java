package com.br.springthymeleaf.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.springthymeleaf.webapp.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByLogin(String login);
}
