package br.com.noemi.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.noemi.blog.entity.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

}
