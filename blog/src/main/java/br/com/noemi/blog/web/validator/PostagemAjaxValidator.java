package br.com.noemi.blog.web.validator;

import org.springframework.validation.BindingResult;

import br.com.noemi.blog.entity.Postagem;

public class PostagemAjaxValidator {

	private Postagem postagem;
	
	private String status;
	
	private String tituloError;
	
	private String textoError;
	
	public void validar(BindingResult result) {
		
		if (result.hasFieldErrors("titulo")) {
			
			this.tituloError = result.getFieldError("titulo").getDefaultMessage();
		}
		
		if (result.hasFieldErrors("texto")) {
			
			this.textoError = result.getFieldError("texto").getDefaultMessage();
		}
	}

	public Postagem getPostagem() {
		return postagem;
	}

	public void setPostagem(Postagem postagem) {
		this.postagem = postagem;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTituloError() {
		return tituloError;
	}

	public void setTituloError(String tituloError) {
		this.tituloError = tituloError;
	}

	public String getTextoError() {
		return textoError;
	}

	public void setTextoError(String textoError) {
		this.textoError = textoError;
	}
	
	
}
