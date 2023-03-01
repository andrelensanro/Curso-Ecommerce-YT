package com.curso.ecommerce.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.curso.ecommerce.demo.model.DetalleOrden;
import com.curso.ecommerce.demo.repository.IDetalleOrdenRepository;

public class DetalleOrdenServiceImpl implements IDetalleOrdenService{

	
	@Autowired
	private IDetalleOrdenRepository detalleOrdenRepository;
	
	@Override
	public DetalleOrden save(DetalleOrden detalleOrden) {
		return detalleOrdenRepository.save(detalleOrden);
	}

}
