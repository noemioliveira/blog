package br.com.noemi.blog.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.noemi.blog.entity.Postagem;
import br.com.noemi.blog.repository.PostagemRepository;
import br.com.noemi.blog.util.ReplaceString;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class PostagemService {

	@Autowired
	private PostagemRepository repository;
	
	public Page<Postagem> findByTituloAndAutor(int page, int size, String titulo, Long id) {
		Pageable pageable = new PageRequest(page, size);
		
		return repository.findAllByAutorIdAndTituloContainingIgnoreCaseOrderByDataPostagemDesc(pageable, id, titulo);
	}
	
	public Page<Postagem> findByTitulo(int page, int size, String titulo) {
		
		Pageable pageable = new PageRequest(page, size);
		
		return repository
				.findAllByTituloContainingIgnoreCaseOrderByDataPostagemDesc(pageable, titulo);
	}
	
	public Page<Postagem> findByTexto(int page, int size, String texto) {
		
		return repository 
				.findByTextoContainingIgnoreCaseOrderByDataPostagemDesc(
						texto, 
						new PageRequest(page, size)
				);
	}
	
	public Page<Postagem> findByPaginationByAutor(int page, int size, Long id) {
		Pageable pageable = new PageRequest(page, size);
		return repository
				.findAllByAutorIdOrderByDataPostagemDesc(pageable, id);
	}
	
	public Page<Postagem> findByPaginationByCategoria(int page, int size, String permalink) {
		Pageable pageable = new PageRequest(page, size);
		return repository
				.findAllByCategoriasPermalinkOrderByDataPostagemDesc(pageable, permalink);
	}
	
	public Page<Postagem> findByPagination(int page, int size) {
		
		Pageable pageable = new PageRequest(page, size);
		return repository.findAllByOrderByDataPostagemDesc(pageable);
	}
	
	public List<Postagem> findAll() {
		
		return repository.findAll();
	}
	
	public List<Postagem> findByAutor(String nome) {
		
		return repository.findByAutorNome(nome);
	}

	public List<Postagem> findByCategoria(String link) {

		return repository.findByCategoriasPermalink(link);
	}
	
	public Postagem findByPermalink(String permalink) {
		
		return repository.findByPermalink(permalink);
	}
	
	public Postagem findById(Long id) {
		
		return repository.findOne(id);
	}
	
	@Transactional(readOnly = false)
	public void delete(Long id) {
		
		repository.delete(id);		
	}
	
	@Transactional(readOnly = false)
	public void saveOrUpdate(Postagem postagem) {
		if (postagem.getId() == null) {
			save(postagem);
		} else {
			update(postagem);
		}
	}

	private void update(Postagem postagem) {
		
		Postagem persistente = repository.findOne(postagem.getId());
		
		if (!persistente.getTitulo().equals(postagem.getTitulo())) {
			persistente.setTitulo(postagem.getTitulo());
		}
		
		if (!persistente.getTexto().equals(postagem.getTexto())) {
			persistente.setTexto(postagem.getTexto());
		}
		
		if (persistente.getCategorias() != postagem.getCategorias()) {
			persistente.setCategorias(postagem.getCategorias());;
		}
		
		repository.save(persistente);		
	}

	private void save(Postagem postagem) {
		
		String permalink = ReplaceString.formatarPermalink(postagem.getTitulo());
		
		postagem.setPermalink(permalink);
		
		postagem.setDataPostagem(LocalDateTime.now());
		
		repository.save(postagem);
	}

}
