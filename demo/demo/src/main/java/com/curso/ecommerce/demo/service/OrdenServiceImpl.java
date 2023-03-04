package com.curso.ecommerce.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.demo.model.Orden;
import com.curso.ecommerce.demo.model.Usuario;
import com.curso.ecommerce.demo.repository.IOrdenRepository;


@Service
public class OrdenServiceImpl implements IOrdenService {
	
	@Autowired
	private IOrdenRepository ordenRepository;
	
	@Override
	public Orden save(Orden orden) {
		
		return ordenRepository.save(orden);
	}
	@Override
	public List<Orden> findAll() {
		return ordenRepository.findAll();
	}
	
	public String generateNumberOrden() {
		int n = 0;
		List<Orden> ordenes = findAll();
		List<Integer> numeros = new ArrayList<Integer>();
		ordenes.stream().forEach(ord -> numeros.add(Integer.parseInt(ord.getNumero())));
		
		if(ordenes.isEmpty()) {
			n = 1;
		}else {
			n = numeros.stream().max(Integer::compare).get();
			n++;
		}
		String nAsString = String.valueOf(n); 
				
		String resp = "";
		for(int i=0; i<10-nAsString.length(); i++) {
			resp+="0";
		}
	
		
		return resp + nAsString;
	}
	@Override
	public List<Orden> findByUsuario(Usuario usuario) {
		
		return ordenRepository.findByUsuario(usuario);
	}
	@Override
	public Optional<Orden> findById(Integer id) {
		return ordenRepository.findById(id);
	}

	
	
}
