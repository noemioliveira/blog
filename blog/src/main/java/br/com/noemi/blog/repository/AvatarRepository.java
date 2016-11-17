package br.com.noemi.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.noemi.blog.entity.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

}
