package br.com.noemi.blog.entity;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class UsuarioLogado extends User {

	private Usuario usuario;

	public UsuarioLogado(Usuario usuario) {
		super(
				usuario.getEmail(),
				usuario.getSenha(),
				AuthorityUtils.createAuthorityList(usuario.getPerfil().toString()));
		
		this.usuario = usuario;
	}
	
	public Perfil getPerfil() {
		return usuario.getPerfil();
	}
	
	public Long getId() {
		return usuario.getId();
	}
}
