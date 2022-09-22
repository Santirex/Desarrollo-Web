package com.sistema.blog.repositorio;

import java.util.Optional;

import com.sistema.blog.entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositorio extends JpaRepository<User, Long>{

	public Optional<User> findByEmail(String email);

	public Optional<User> findById(Long id);
	
	public Optional<User> findByUsernameOrEmail(String username, String email);
	
	public Optional<User> findByUsername(String username);
	
	public Boolean existsByUsername(String username);

	public boolean existsById(Long id);

	public Boolean existsByEmail(String email);
	
}
