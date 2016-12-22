package br.com.noemi.blog.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.noemi.blog.entity.Avatar;
import br.com.noemi.blog.entity.Perfil;
import br.com.noemi.blog.entity.Usuario;
import br.com.noemi.blog.repository.UsuarioRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	public Page<Usuario> findByPaginationOrderByField(int page, int size, String field, String order) {
		
		Sort sort = new Sort(new Order(Direction.fromString(order), field));
		
		return repository.findAll(new PageRequest(page, size, sort));
	}
	
	public Page<Usuario> findByPagination(int page, int size) {
		
		Pageable pageable = new PageRequest(page, size);
		
		return repository.findAllByOrderByNomeAsc(pageable);
	}
	
	@Transactional
	public void updatePerfil(Usuario usuario) {
		
		Usuario persistente = repository.findOne(usuario.getId());
		persistente.setPerfil(usuario.getPerfil());
		
		repository.save(persistente);		
	}
	
	@Transactional(readOnly = false)
	public void updateSenha(Usuario usuario) {
		
		String hash = new BCryptPasswordEncoder().encode(usuario.getSenha());
		
		Usuario uPersistente = repository.findOne(usuario.getId());
		
		uPersistente.setSenha(hash);
		
		//repository.updateSenha(usuario.getSenha(), usuario.getId());
		
		repository.save(uPersistente);
	}
	
	@Transactional(readOnly = false)
	public void updateNomeAndEmail(Usuario usuario) {
		
		Usuario uPersistente = repository.findOne(usuario.getId());
		uPersistente.setNome(usuario.getNome());
		uPersistente.setEmail(usuario.getEmail());
		
		//repository.updateNomeAndEmail(usuario.getNome(), usuario.getEmail(), usuario.getId());
		
		repository.save(uPersistente);
	}
	
	@Transactional(readOnly = false)
	public void save(Usuario usuario) {
		
		if (usuario.getDataCadastro() == null) {
			usuario.setDataCadastro(LocalDate.now());
		}
		
		String hash = new BCryptPasswordEncoder().encode(usuario.getSenha());
		
		usuario.setPerfil(Perfil.LEITOR);
		
		usuario.setSenha(hash);
		
		repository.save(usuario);
	}
	
	@Transactional(readOnly = false)
	public void delete(Long id) {
		
		repository.delete(id);
	}
	
	public Usuario findById(Long id) {
		
		return repository.findOne(id);
	}
	
	public Usuario findByAvatar(Avatar avatar) {
		
		return repository.findByAvatar(avatar);
	}
	
	public Usuario findByEmail(String email) {
		
		return repository.findByEmail(email);
	}
	
	public List<Usuario> findAll() {
		
		return repository.findAll();
	}

}
