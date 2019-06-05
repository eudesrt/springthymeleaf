package com.br.springthymeleaf.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.springthymeleaf.webapp.entity.Grupo;
import com.br.springthymeleaf.webapp.entity.Permissao;

public interface PermissaoRepository  extends JpaRepository<Permissao, Long>{
	
	List<Permissao> findByGruposIn(Grupo grupoEntity);

}
