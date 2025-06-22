package com.backend.juvaro.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(length = 200)
    private String descripcion;

    @Column(name = "imagen_url", length = 2048)
    private String imagenUrl;

    @Column
    private Double Precio;

    @ManyToOne
    @JoinColumn(name="categoria_id")
    private Categoria categoria;

}
