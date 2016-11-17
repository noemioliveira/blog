package br.com.noemi.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.noemi.blog.entity.Avatar;
import br.com.noemi.blog.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByEmail(String email);
	
	Usuario findByAvatar(Avatar avatar);

	@Modifying
	@Query("update Usuario u set u.nome = ?1, u.email =?2  where u.id = ?3")
	void updateNomeAndEmail(String nome, String email, Long id);
} 
