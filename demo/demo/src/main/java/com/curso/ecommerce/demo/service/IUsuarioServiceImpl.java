package com.curso.ecommerce.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.demo.model.Usuario;
import com.curso.ecommerce.demo.repository.IUsuarioRepository;


@Service
public class IUsuarioServiceImpl implements IUsuarioService {
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Override
	public Usuario findById ( Integer id ) {

		Optional<Usuario> usuario = usuarioRepository.findById(id);
		return usuario.get(); 
	}

	@Override
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);	 
	}
	
	@Override
	public Optional<Usuario> findByEmail(String email) {

		return usuarioRepository.findByEmail(email);
	}

}
