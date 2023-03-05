package com.curso.ecommerce.demo.controller;


import java.util.List;
import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.demo.model.Orden;
import com.curso.ecommerce.demo.model.Usuario;
import com.curso.ecommerce.demo.service.IOrdenService;
import com.curso.ecommerce.demo.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private final Logger logger = LoggerFactory.getLogger(UsuarioController.class); 

	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IOrdenService ordenService;
	
	@GetMapping("/registro")
	public String create() {
			
		return "usuario/registro";
	}
	@PostMapping("/save")
	public String save(Usuario usuario) {
		usuario.setTipo("USER");
		usuarioService.save(usuario);
		logger.info("Tenemos al siguiente usuario ", usuario);
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "usuario/login";
	}
	
	@PostMapping("/access")
	public String acceder(Usuario usuario, HttpSession session) {
		Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());
		logger.info("Accedio {}", usuario);
		
		if(user.isPresent()) {
			session.setAttribute("idusuario", user.get().getId());
			logger.info("El id del usuario en usuario controller es {}", user.get().getId());
			if(user.get().getTipo().equals("admin")) {
				return "redirect:/administrador";
			}
		}else {
			logger.info("usuario no existe");
		}
		
		
		return "redirect:/";
	}
	
	@GetMapping("/shopping")
	public String comprar(Model model, HttpSession session) {
		
		model.addAttribute("sesion", session.getAttribute("idusuario")); 
		
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
		List<Orden> ordenes = ordenService.findByUsuario(usuario);
		
		model.addAttribute("ordenes", ordenes);
		
		
		return "usuario/compras";
	}
	
	@GetMapping("/detail/{id}")
	
	public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model ) {
		
		Optional<Orden> orden = ordenService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
		
		model.addAttribute("detalles", orden.get().getDetalleOrden());
		logger.info("Orden id {} ", orden.get().getId());
		
		
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		
		return "usuario/detallecompra";
	}
	@GetMapping("/logout")
	public String cerrar_sesion(HttpSession session) {
		session.removeAttribute("idusuario");
		return "redirect:/";
	}
	
}
