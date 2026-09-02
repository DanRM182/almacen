package com.christian.almacen.entities;

import com.christian.almacen.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SUCURSALES")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SUCURSAL")
    private Long id;

    @Column(name = "NOMBRE", length = 50, nullable = false)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 150, nullable = false)
    private String descripcion;

    public void validarDatos(String nombre, String descripcion) {
        StringCustomUtils.validarTamanio(nombre, 5, 50, "El nombre debe tener entre 5 y 50 caracteres");
        StringCustomUtils.validarTamanio(descripcion, 5, 150, "La descripción debe tener entre 5 y 150 caracteres");

    }
}
