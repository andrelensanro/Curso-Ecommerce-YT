package com.curso.ecommerce.demo.service;

import java.util.List;
import java.util.Optional;
import com.curso.ecommerce.demo.model.Producto;

public interface ProductoService{
	public Producto save(Producto producto);
	/*
	 * Optional nos dice si el objeto existe en la base de datos. 
	 * 
	 * */
	public Optional<Producto> get(Integer id);
	public void update(Producto producto);
	public void delete (Integer id);
	public List<Producto> findAll();
}
