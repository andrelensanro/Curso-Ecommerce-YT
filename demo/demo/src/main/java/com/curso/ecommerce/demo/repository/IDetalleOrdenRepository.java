package com.curso.ecommerce.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.ecommerce.demo.model.DetalleOrden;



public interface IDetalleOrdenRepository  extends JpaRepository<DetalleOrden, Integer> {

}
