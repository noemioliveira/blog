package br.com.noemi.blog.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "comentarios")
public class Comentario extends AbstractPersistable<Long> implements Comparable<Comentario> {

	@NotBlank
	@Length(min = 5, max = 255, message = "Seu comentário deve conter entre 5 e 255 caracteres.")
	@Column(nullable = false, columnDefinition = "TEXT")
	private String texto;
	
	@Column(name = "data_comentario")
	private LocalDateTime dataComentario;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Postagem postagem;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Usuario usuario;
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getDataComentario() {
		return dataComentario;
	}

	public void setDataComentario(LocalDateTime dataComentario) {
		this.dataComentario = dataComentario;
	}

	public Postagem getPostagem() {
		return postagem;
	}

	public void setPostagem(Postagem postagem) {
		this.postagem = postagem;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int compareTo(Comentario comentario) {
		
		return comentario.getDataComentario().compareTo(this.dataComentario);
	}
}
