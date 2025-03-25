package com.agricultura.plataformaAgricola.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.agricultura.plataformaAgricola.model.Producto;
import com.agricultura.plataformaAgricola.repository.ProductoRespository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductoService {

	private final ProductoRespository productoRepository;
	
	@Transactional
	public Producto crearProducto(Producto producto, MultipartFile imagen) throws IOException {
        if (imagen != null && !imagen.isEmpty()) {
        	producto.setImagen_url(imagen.getBytes());
        }
		return productoRepository.save(producto);
	}
	
	public Producto obtenerProducto(Long id) {
		return productoRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontro producto con el ID :" + id));
	}
	
	@Transactional(readOnly = true)
	public List<Producto> listarProducto(){
		return productoRepository.findAll();
	}
	
	@Transactional
	public Producto editarProducto(Producto producto, Long id, MultipartFile imagen) throws IOException {
		Producto editarProducto = productoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

		editarProducto.setNombre(producto.getNombre());
		editarProducto.setDescripcion(producto.getDescripcion());
		editarProducto.setStock(producto.getStock());
		editarProducto.setPrecio(producto.getPrecio());
		editarProducto.setImagen_url(producto.getImagen_url());
		
		if (imagen != null && !imagen.isEmpty()) {
			editarProducto.setImagen_url(imagen.getBytes());
		}
		
		editarProducto.setDisponible(producto.getDisponible());

		return productoRepository.save(editarProducto);
	}
	
	@Transactional
	public ResponseEntity<String> eliminarProducto(Long id) {
	    if (!productoRepository.existsById(id)) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("El producto con ID " + id + " no existe.");
	    }
	    productoRepository.deleteById(id);
	    return ResponseEntity.noContent().build(); // 204 No Content si se elimina correctamente
	}

	public ResponseEntity<Producto> buscarProductoPorID(Long id){
		return productoRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

}
