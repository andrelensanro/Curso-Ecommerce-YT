package com.curso.ecommerce.demo.service;

import java.util.List;

import com.curso.ecommerce.demo.model.Orden;

public interface IOrdenService {
	Orden save (Orden orden);
	List<Orden> findAll();
	String generateNumberOrden();
}
