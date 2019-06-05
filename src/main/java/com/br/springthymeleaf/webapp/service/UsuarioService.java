package com.br.springthymeleaf.webapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.br.springthymeleaf.webapp.entity.Grupo;
import com.br.springthymeleaf.webapp.entity.Permissao;
import com.br.springthymeleaf.webapp.entity.Usuario;
import com.br.springthymeleaf.webapp.model.UsuarioModel;
import com.br.springthymeleaf.webapp.model.UsuarioSecurityModel;
import com.br.springthymeleaf.webapp.repository.GrupoRepository;
import com.br.springthymeleaf.webapp.repository.PermissaoRepository;
import com.br.springthymeleaf.webapp.repository.UsuarioRepository;

@Component
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private PermissaoRepository permissaoRepository;

	/***
	 * CONSULTA UM USUÁRIO POR LOGIN
	 */
	@Override
	public UserDetails loadUserByUsername(String login) throws BadCredentialsException, DisabledException {

		Usuario usuarioEntity = usuarioRepository.findByLogin(login);

		if (usuarioEntity == null)
			throw new BadCredentialsException("Usuário não encontrado no sistema!");

		if (!usuarioEntity.isAtivo())
			throw new DisabledException("Usuário não está ativo no sistema!");

		return new UsuarioSecurityModel(usuarioEntity.getLogin(), usuarioEntity.getSenha(), usuarioEntity.isAtivo(),
				this.buscarPermissoesUsuario(usuarioEntity));
	}

	/***
	 * BUSCA AS PERMISSÕES DO USUÁRIO
	 * 
	 * @param usuarioEntity
	 * @return
	 */
	public List<GrantedAuthority> buscarPermissoesUsuario(Usuario usuarioEntity) {

		List<Grupo> grupos = grupoRepository.findByUsuariosIn(usuarioEntity);

		return this.buscarPermissoesDosGrupos(grupos);
	}

	/***
	 * BUSCA AS PERMISSÕES DO GRUPO
	 */
	public List<GrantedAuthority> buscarPermissoesDosGrupos(List<Grupo> grupos) {
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();

		for (Grupo grupo : grupos) {

			List<Permissao> lista = permissaoRepository.findByGruposIn(grupo);

			for (Permissao permissao : lista) {
				auths.add(new SimpleGrantedAuthority(permissao.getPermissao()));
			}
		}

		return auths;
	}

	/***
	 * SALVA UM NOVO REGISTRO DE USUÁRIO
	 * 
	 * @param usuarioModel
	 */
	public void salvarUsuario(UsuarioModel usuarioModel) {

		Usuario usuarioEntity = new Usuario();

		/* SETA O USUÁRIO COMO ATIVO NO SISTEMA */
		usuarioEntity.setAtivo(true);

		/* LOGIN DO USUÁRIO */
		usuarioEntity.setLogin(usuarioModel.getLogin());

		/* NOME DO USUÁRIO A SER SALVO */
		usuarioEntity.setNome(usuarioModel.getNome());

		/* CRIPTOGRAMA E INFORMA A SENHA */
		usuarioEntity.setSenha(new BCryptPasswordEncoder().encode(usuarioModel.getSenha()));

		/* PEGANDO A LISTA DE GRUPOS SELECIONADOS */
		Grupo grupoEntity = null;
		List<Grupo> grupos = new ArrayList<Grupo>();
		for (Long codigoGrupo : usuarioModel.getGrupos()) {

			if (codigoGrupo != null) {

				/* CONSULTA GRUPO POR CÓDIGO */
				grupoEntity = grupoRepository.getOne(codigoGrupo);

				/* ADICIONA O GRUPO NA LISTA */
				grupos.add(grupoEntity);
			}
		}

		/* SETA A LISTA DE GRUPO DO USUÁRIO */
		usuarioEntity.setGrupos(grupos);

		/* SALVANDO O REGISTRO */
		this.usuarioRepository.save(usuarioEntity);
	}

	/***
	 * CONSULTA OS USUÁRIOS CADASTRADOS
	 * 
	 * @return
	 */
	public List<UsuarioModel> consultarUsuarios() {

		List<UsuarioModel> usuariosModel = new ArrayList<UsuarioModel>();

		List<Usuario> usuariosEntity = this.usuarioRepository.findAll();

		usuariosEntity.forEach(usuarioEntity -> {

			usuariosModel.add(new UsuarioModel(usuarioEntity.getCodigo(), usuarioEntity.getNome(),
					usuarioEntity.getLogin(), null, usuarioEntity.isAtivo(), null));
		});

		return usuariosModel;
	}

	/**
	 * DELETA UM USUÁRIO PELO CÓDIGO
	 */
	public void excluir(Long codigoUsuario) {

		this.usuarioRepository.deleteById(codigoUsuario);
	}

	/***
	 * CONSULTA UM USUÁRIO PELO SEU CÓDIGO
	 * 
	 * @param codigoUsuario
	 * @return
	 */
	public UsuarioModel consultarUsuario(Long codigoUsuario) {

		Usuario usuarioEntity = this.usuarioRepository.getOne(codigoUsuario);

		List<Long> grupos = new ArrayList<Long>();

		usuarioEntity.getGrupos().forEach(grupo -> {

			grupos.add(grupo.getCodigo());

		});

		return new UsuarioModel(usuarioEntity.getCodigo(), usuarioEntity.getNome(), usuarioEntity.getLogin(), null,
				usuarioEntity.isAtivo(), grupos);

	}

	/**
	 * ALTERA AS INFORMAÇÕES DO USUÁRIO
	 */
	public void alterarUsuario(UsuarioModel usuarioModel) {

		Usuario usuarioEntity = this.usuarioRepository.getOne(usuarioModel.getCodigo());

		/* USUÁRIO ATIVO OU INATIVO */
		usuarioEntity.setAtivo(usuarioModel.isAtivo());

		/* LOGIN DO USUÁRIO */
		usuarioEntity.setLogin(usuarioModel.getLogin());

		/* NOME DO USUÁRIO A SER SALVO */
		usuarioEntity.setNome(usuarioModel.getNome());

		/* CRIPTOGRAMA E INFORMA A SENHA */
		if (!StringUtils.isEmpty(usuarioModel.getSenha()))
			usuarioEntity.setSenha(new BCryptPasswordEncoder().encode(usuarioModel.getSenha()));

		/* PEGANDO A LISTA DE GRUPOS SELECIONADOS */
		Grupo grupoEntity = null;
		List<Grupo> grupos = new ArrayList<Grupo>();
		for (Long codigoGrupo : usuarioModel.getGrupos()) {

			if (codigoGrupo != null) {

				/* CONSULTA GRUPO POR CÓDIGO */
				grupoEntity = grupoRepository.getOne(codigoGrupo);

				/* ADICIONA O GRUPO NA LISTA */
				grupos.add(grupoEntity);
			}
		}

		/* SETA A LISTA DE GRUPO DO USUÁRIO */
		usuarioEntity.setGrupos(grupos);

		/* SALVANDO ALTERAÇÃO DO REGISTRO */
		this.usuarioRepository.saveAndFlush(usuarioEntity);
	}

}
