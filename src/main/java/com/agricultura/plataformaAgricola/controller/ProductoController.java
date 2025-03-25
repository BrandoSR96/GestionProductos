package com.agricultura.plataformaAgricola.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.agricultura.plataformaAgricola.model.Producto;
import com.agricultura.plataformaAgricola.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/api/productos")
@AllArgsConstructor
public class ProductoController {
	
	private final ProductoService productoService;

	@PostMapping("/crear")
	public ResponseEntity<Producto> crearProducto(
	        @RequestPart("producto") String productoJson,
	        @RequestPart(value = "imagen", required = false) MultipartFile imagen) throws IOException {

	    ObjectMapper objectMapper = new ObjectMapper();
	    Producto producto = objectMapper.readValue(productoJson, Producto.class);

	    Producto crearProducto = productoService.crearProducto(producto, imagen);
	    return ResponseEntity.status(HttpStatus.CREATED).body(crearProducto);
	}
	
	@GetMapping("/imagen/{id}")
	public ResponseEntity<byte[]> obtenerImagen(@PathVariable Long id){
		Producto producto = productoService.obtenerProducto(id);
		
		if (producto == null || producto.getImagen_url() == null) {
			return ResponseEntity.notFound().build();
		}
		
		MediaType mediaType = MediaType.IMAGE_JPEG;
		
		if (producto.getNombre() != null && producto.getNombre().toLowerCase().endsWith(".png")) {
			mediaType = MediaType.IMAGE_PNG;
		}
		
		return ResponseEntity.ok().contentType(mediaType).body(producto.getImagen_url());
	}

	
	@GetMapping
	public ResponseEntity<List<Producto>> listarProductos(){
		return ResponseEntity.ok(productoService.listarProducto());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Producto> editarProducto(
			@RequestPart("producto") String productoJson, 
			@PathVariable Long id,
			@RequestPart(value = "imagen", required = false) MultipartFile imagen) throws IOException{
		
		ObjectMapper objectMapper = new ObjectMapper();
		Producto producto = objectMapper.readValue(productoJson, Producto.class);
				
		Producto editarProducto = productoService.editarProducto(producto, id, imagen);
		return ResponseEntity.ok(editarProducto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
		try {
			productoService.eliminarProducto(id);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.noContent().build();	
		}		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Producto> buscarProductoPorId(@PathVariable Long id){
		return productoService.buscarProductoPorID(id);
		
	}
	
}
