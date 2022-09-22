package com.sistema.blog.repositorio;

import java.util.Optional;

import com.sistema.blog.servicio.UserServicio;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.blog.entidades.Rol;

public interface RolRepositorio extends JpaRepository<Rol, Long>{

	public Optional<Rol> findById(Long id);


}
