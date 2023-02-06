package com.curso.ecommerce.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.curso.ecommerce.demo.model.Producto;


/*
 * 
 * Jpa<Producto, Integer>
 * Proudcto, es a la tabla a la que estará afectando
 * Integer hace referencia al tipo de dato del ID del producto, ya que 
 * se estará haciendo referencia a este para hacer las modificaciones en la base de datos.
 * 
 * */

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{
	

}
