package com.curso.ecommerce.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.curso.ecommerce.demo.model.Producto;
import com.curso.ecommerce.demo.repository.ProductoRepository;

/*De tal manera que nos permita inyectar en el controlador*/

@Service
public class ProductoServiceImpl implements ProductoService{

	// Esta anotacion indica que estamos inyectando a esta clase un objeto
	@Autowired
	// El autowired en este caso nos trae la dependencia que 
	// tenemos en ProductoRepository, que aunque no definio nada, 
	// este por defecto ya trae funciones que podemos usar. 
	private ProductoRepository productoRepository;
	
	@Override
	public Producto save(Producto producto) {
		return productoRepository.save(producto);
	}

	@Override
	public Optional<Producto> get(Integer id) {
		return productoRepository.findById(id);
	}

	@Override
	public void update(Producto producto) {
		// el metodo save de JPA se comporta de la siguiente manera:
		// si le pasas un objeto cuyo id ya existe, se sobreescribe
		// en caso de que no exista se crea.
		productoRepository.save(producto);
	}

	@Override
	public void delete(Integer id) {
		productoRepository.deleteById(id);
	}
	

}
