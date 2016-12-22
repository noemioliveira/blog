package br.com.noemi.blog.entity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "postagens")
public class Postagem extends AbstractPersistable<Long> {

	@NotBlank
	@Length(min = 5, max = 60)
	@Column(nullable = false, unique = true, length = 60)
	private String titulo;
	
	@NotBlank
	@Column(nullable = false, columnDefinition = "LONGTEXT")
	private String texto;
	
	@Column(nullable = false, unique = true, length = 60)
	private String permalink;
	
	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm:ss") 
	@Column(name = "data_postagem", nullable = false)
	private LocalDateTime dataPostagem;
	
	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Autor autor;	
	
	//@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "postagens_has_categorias",	
		joinColumns = @JoinColumn(name = "postagem_id"),
		inverseJoinColumns = @JoinColumn(name = "categoria_id")
	)
	private List<Categoria> categorias;
	
	@OneToMany(mappedBy = "postagem", fetch = FetchType.EAGER)
	private List<Comentario> comentarios;

	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public LocalDateTime getDataPostagem() {
		return dataPostagem;
	}

	public void setDataPostagem(LocalDateTime dataPostagem) {
		this.dataPostagem = dataPostagem;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Comentario> getComentarios() {
		if (comentarios != null) {
			Collections.sort(comentarios);
		}
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
}
