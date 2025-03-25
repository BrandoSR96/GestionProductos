package com.agricultura.plataformaAgricola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agricultura.plataformaAgricola.model.Producto;

@Repository
public interface ProductoRespository extends JpaRepository<Producto, Long>{

}
