package br.com.noemi.blog.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.noemi.blog.entity.UsuarioLogado;

@ControllerAdvice
public class UsuarioLogadoController {

	@ModelAttribute("logado")
	public UsuarioLogado getUsuarioLogado(Authentication authentication) {
		
		return (authentication == null) ? null : (UsuarioLogado) authentication.getPrincipal();
	}
}
