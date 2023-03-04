package com.curso.ecommerce.demo.controller;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.curso.ecommerce.demo.model.Producto;
import com.curso.ecommerce.demo.model.Usuario;
import com.curso.ecommerce.demo.service.IUsuarioService;
import com.curso.ecommerce.demo.service.ProductoService;
import com.curso.ecommerce.demo.service.UploadFileService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private  ProductoService productoService;
	@Autowired
	private UploadFileService uploadFileService;
	@Autowired
	private IUsuarioService usuarioService;
	
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
	public String save(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {	
		
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
		producto.setUsuario(usuario);
		
		//manejo de la imagen	
		// caso: el producto que se guarda es su primera vez
		String nameImage = uploadFileService.saveImage(file);
		producto.setImagen(nameImage);
		
		
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
	
	@PostMapping("/update")
	public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
		
		/*
		 * EL PRODUCTO QUE SE OBTIENE COMO PARAMETRO TIENE TODOS LOS CAMPOS MENOS LA IMAGEN
		 * 
		 * siempre tiene una imagen "default.jpg"
		 * EL UPDATE PUEDE HACERSE primero no tiene imagen ("default.jpg") y despues si tiene una imagen
		 * 						   primero tiene y se queda con la imagen que tiene
		 *                         
		 * 
		 * */
		
		
		Producto p = new Producto();
		p = productoService.get(producto.getId()).get();
		
		
		if(file.isEmpty()) {
			producto.setImagen(p.getImagen());
		}else {//cuando se edita tambien la imagen hay que borrar la anterior
			if(!p.getImagen().equals("default.png")){
				uploadFileService.deleteImage(p.getImagen());
			}
			String nameImage = uploadFileService.saveImage(file);
			producto.setImagen(nameImage);
		}
		producto.setUsuario(p.getUsuario());
		productoService.update(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		
		Producto p = new Producto();
		p = productoService.get(id).get();
		
		if(!p.getImagen().equals("default.jpg")) {
			uploadFileService.deleteImage(p.getImagen());
		}
		
		productoService.delete(id);
		return "redirect:/productos";
	}
 
	
}
