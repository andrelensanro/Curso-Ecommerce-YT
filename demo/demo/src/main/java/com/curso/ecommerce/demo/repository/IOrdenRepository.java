package com.curso.ecommerce.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.ecommerce.demo.model.Orden;

public interface IOrdenRepository extends JpaRepository<Orden, Integer>{

}
