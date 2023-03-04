package com.curso.ecommerce.demo.service;

import java.util.List;
import java.util.Optional;
import com.curso.ecommerce.demo.model.Orden;
import com.curso.ecommerce.demo.model.Usuario;

public interface IOrdenService {
	Orden save (Orden orden);
	List<Orden> findAll();
	String generateNumberOrden();
	List<Orden> findByUsuario(Usuario usuario);
	Optional<Orden> findById(Integer id);
}
