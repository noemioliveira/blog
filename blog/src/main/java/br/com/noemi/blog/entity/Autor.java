package br.com.noemi.blog.entity;

import java.util.List;

import javax.persistence.*;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "autores")
public class Autor extends AbstractPersistable<Long> {
	
	@NotBlank
	@Length(min = 3, max = 50)
	@Column(nullable = false, unique = true, length = 50)
	private String nome;
	
	@NotBlank(message = "Este campo não aceita valor em branco.")
	@Length(min = 5, max = 255, message = "Este campo aceita entre 5 e 255 caracteres.")
	@Column(nullable = false, length = 255)
	private String biografia;
	
	@OneToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "autor")
	private List<Postagem> postagens;
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Postagem> getPostagens() {
		return postagens;
	}

	public void setPostagens(List<Postagem> postagens) {
		this.postagens = postagens;
	}
}
