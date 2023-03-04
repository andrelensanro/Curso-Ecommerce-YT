package com.curso.ecommerce.demo.service;

import java.util.Optional;

import com.curso.ecommerce.demo.model.Usuario;

public interface IUsuarioService {
	
	Usuario findById(Integer id);
	Usuario save (Usuario usuario);
	Optional<Usuario> findByEmail(String email);
}
