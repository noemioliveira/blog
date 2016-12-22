package br.com.noemi.blog.entity;

import java.util.List;

import javax.persistence.*;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "categorias")
public class Categoria extends AbstractAuditoria<Long> {

	@NotBlank
	@Length(min = 3, max = 30)
	@Column(nullable = false, unique = true, length = 30)
	private String descricao;
	
	@Column(nullable = false, unique = true, length = 30)
	private String permalink;
	
	@ManyToMany
	@JoinTable(
		name = "postagens_has_categorias",
		joinColumns = @JoinColumn(name = "categoria_id"),
		inverseJoinColumns = @JoinColumn(name = "postagem_id")
	)
	private List<Postagem> postagens;	

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public List<Postagem> getPostagens() {
		return postagens;
	}

	public void setPostagens(List<Postagem> postagens) {
		this.postagens = postagens;
	}
}
