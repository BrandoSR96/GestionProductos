package com.agricultura.plataformaAgricola.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "producto")
@NoArgsConstructor
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private String descripcion;

	private Integer stock;

	@Column(nullable = false)
	private Double precio;

    @Lob
    private byte[] imagen_url;

	@Column(nullable = false)
	private Boolean disponible = true;
}