package com.curso.ecommerce.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.curso.ecommerce.demo.model.DetalleOrden;
import com.curso.ecommerce.demo.model.Orden;
import com.curso.ecommerce.demo.model.Producto;
import com.curso.ecommerce.demo.model.Usuario;
import com.curso.ecommerce.demo.service.IDetalleOrdenService;
import com.curso.ecommerce.demo.service.IOrdenService;
import com.curso.ecommerce.demo.service.IUsuarioService;
import com.curso.ecommerce.demo.service.ProductoService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/")

public class HomeController {
	
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IOrdenService ordenService;
	@Autowired
	private IDetalleOrdenService detalleOrdenService;
	
	private final Logger log = LoggerFactory.getLogger(HomeController.class);
	//Para almacenar los detalles de la orden
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
	//Datos de la orden
	Orden orden = new Orden(); 
	
	
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("/")
	public String home(Model model, HttpSession session) {
		log.info("Ha iniciado session {}", session.getAttribute("idusuario"));
		model.addAttribute("productos", productoService.findAll());
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "usuario/home";
	}
	
	@GetMapping("productohome/{id}")
	public String productoHome(@PathVariable Integer id, Model model) {
		log.info("Id producto enviado como parametro {}", id);
		
		Producto producto = new Producto();
		Optional<Producto> productoOptional = productoService.get(id);
		producto = productoOptional.get();
		
		model.addAttribute("producto",producto );
		
		return "usuario/productohome";
	}
	
	@PostMapping("/cart")
	public String addCart(@RequestParam(name="id") int id, @RequestParam Integer cantidad, Model model){
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = new Producto();
		double sumaTotal = 0;
		
		Optional<Producto> optionalProducto = productoService.get(id);		
		producto = optionalProducto.get();
		
		
		detalleOrden.setPrecio(producto.getPrecio());
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setTotal(producto.getPrecio()*cantidad);
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setProducto(producto);

		/*Validar (con una funcion lambda) que el mismo productpo no se agregue dos veces*/
		Integer idProducto = producto.getId();
		boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId()==idProducto);
		
		
		
		
		if (!ingresado) {
			detalles.add(detalleOrden);
		}else{
			for(DetalleOrden p: detalles) {
				if(p.getProducto().getId() ==idProducto) {
					p.setCantidad(p.getCantidad()+detalleOrden.getCantidad()) ;
					p.setTotal(p.getCantidad()*p.getPrecio());
				}
			}			
		}
		
		//detalles.add(detalleOrden);
		
		sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		
		orden.setTotal(sumaTotal);
		
		model.addAttribute("detalles", detalles);
		model.addAttribute("orden", orden);
		
		log.info("Producto añadido:{}",optionalProducto.get());
		log.info("Cantidad:{}", cantidad);
		
		return "usuario/carrito";
	}
	
	@GetMapping("/delete/cart/{id}")
	public String deleteProductoCart(@PathVariable Integer id, Model model) {
		List<DetalleOrden> listaNueva = new ArrayList<DetalleOrden>();
		
		for(DetalleOrden det: detalles) {
			if(det.getProducto().getId()!=id) {
				listaNueva.add(det);
			}
		}
		detalles = listaNueva;
		
		double sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		
		orden.setTotal(sumaTotal);
		
		model.addAttribute("detalles", detalles);
		model.addAttribute("orden", orden);
		
		return "usuario/carrito";
	}
	
	@GetMapping("/getCart")
	public String getCarrtito(Model model, HttpSession session) {
		model.addAttribute("detalles", detalles);
		model.addAttribute("orden", orden);
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "usuario/carrito";
	}
	
	
	@GetMapping("/order")
	public String order (Model model, HttpSession session) {
		
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
		
		model.addAttribute("detalles", detalles);
		model.addAttribute("orden", orden);
		model.addAttribute("usuario", usuario);
		
		return "usuario/resumenorden";
	}
	
	@GetMapping("/saveOrder")
	public String saveOrder(HttpSession session) {
		Date fechaCreacion = new Date();
		orden.setFechaCreacion(fechaCreacion);
		orden.setNumero(ordenService.generateNumberOrden());
		
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
		orden.setUsuario(usuario);
		ordenService.save(orden);
		
		for(DetalleOrden dto : detalles) {
			dto.setOrden(orden);
			detalleOrdenService.save(dto);
		}
		
		// limpiar 
		orden = new Orden();
		detalles.clear();
		
		return "redirect:/";
	}
	
	@PostMapping("/search")
	public String searchProduct(@RequestParam String name, Model model) {
		
		log.info("Nombre del producto "+name.toLowerCase());
		List<Producto> productos = new ArrayList<Producto>();
		productos = productoService.findAll().stream().filter(p -> p.getNombre().toLowerCase().contains(name)).collect(Collectors.toList());
		model.addAttribute("productos", productos);
		return "usuario/home";
	}
	
}


















