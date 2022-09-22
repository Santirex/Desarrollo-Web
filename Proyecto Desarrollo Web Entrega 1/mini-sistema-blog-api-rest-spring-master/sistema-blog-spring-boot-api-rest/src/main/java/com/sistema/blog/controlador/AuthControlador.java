package com.sistema.blog.controlador;

import java.util.Collections;

import com.sistema.blog.entidades.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.blog.dto.LoginDTO;
import com.sistema.blog.dto.RegistroDTO;
import com.sistema.blog.entidades.Rol;
import com.sistema.blog.repositorio.RolRepositorio;
import com.sistema.blog.repositorio.UserRepositorio;
import com.sistema.blog.seguridad.JWTAuthResonseDTO;
import com.sistema.blog.seguridad.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthControlador {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepositorio userRepositorio;
	
	@Autowired
	private RolRepositorio rolRepositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/iniciarSesion")
	public ResponseEntity<JWTAuthResonseDTO> authenticateUser(@RequestBody LoginDTO loginDTO){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//obtenemos el token del jwtTokenProvider
		String token = jwtTokenProvider.generarToken(authentication);
		
		return ResponseEntity.ok(new JWTAuthResonseDTO(token));
	}
	
	@PostMapping("/registrar")
	public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDTO registroDTO){
		if(userRepositorio.existsByUsername(registroDTO.getUsername())) {
			return new ResponseEntity<>("Ese nombre de user ya existe",HttpStatus.BAD_REQUEST);
		}
		
		if(userRepositorio.existsByEmail(registroDTO.getEmail())) {
			return new ResponseEntity<>("Ese email de user ya existe",HttpStatus.BAD_REQUEST);
		}
		
		User user = new User();
		user.setNombre(registroDTO.getNombre());
		user.setUsername(registroDTO.getUsername());
		user.setEmail(registroDTO.getEmail());
		user.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
		
		//Rol roles = rolRepositorio.findById().get();
		//user.setRoles(Collections.singleton(roles));
		
		userRepositorio.save(user);
		return new ResponseEntity<>("User registrado exitosamente",HttpStatus.OK);
	}
}
