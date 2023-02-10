package com.curso.ecommerce.demo.controller;

import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.curso.ecommerce.demo.model.Producto;
import com.curso.ecommerce.demo.model.Usuario;
import com.curso.ecommerce.demo.service.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private  ProductoService productoService;
	
	@GetMapping("")
	public String show(Model model) {
		model.addAttribute("productos", productoService.findAll());
		return "productos/show";
	}
	@GetMapping("/create")
	public String create(){
		return "productos/create";
		
	}
	@PostMapping("/save")
	public String save(Producto producto) {	
		Usuario usuario = new Usuario(1, "", "", "", "", "", "", "" );
		producto.setUsuario(usuario);
		productoService.save(producto);
		LOGGER.info("Este es el objeto producto {}", producto);
		return "redirect:/productos";
	}
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		Producto producto = new Producto();
		Optional<Producto>  optionalProducto = productoService.get(id);
		producto = optionalProducto.get();
		model.addAttribute("producto", producto);
		
		LOGGER.info("Producto buscado {}", producto);
		return "productos/edit";
	}
	
}
