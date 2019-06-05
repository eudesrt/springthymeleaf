package com.br.springthymeleaf.webapp.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_grupo")
public class Grupo {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "id_grupo")
	private Long codigo;

	@Column(name = "ds_nome")
	private String nome;

	@Column(name = "ds_descricao")
	private String descricao;

	@ManyToMany
	@JoinTable(name = "TB_USUARIO_X_GRUPO", joinColumns = @JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo"), inverseJoinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario"))
	private List<Usuario> usuarios;

	@ManyToMany
	@JoinTable(name = "TB_PERMISSAO_X_GRUPO", joinColumns = @JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo"), inverseJoinColumns = @JoinColumn(name = "id_permissao", referencedColumnName = "id_permissao"))

	private List<Permissao> permissoes;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}
}
