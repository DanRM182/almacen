package com.christian.almacen.dto.productos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Información de un producto")
public record ProductoRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 30, message = "EL nombre debe tener entre 5 y 30 caracteres")
        @Schema(description = "Nombre del producto", example = "Laptop Gamer")
        String nombre,

        @Schema(description = "Categoría del producto", example = "Electrónica")
        @NotBlank(message = "La categoria es requerida")
        String categoria,

        @Schema(description = "Precio del producto", example = "15999.99")
        @NotNull(message = "El precio es requerido")
        @Positive(message = "El precio debe ser positivo")
        BigDecimal precio,

        @Schema(description = "Cantidad disponible del producto", example = "300")
        @NotNull(message = "La cantidad es requerida")
        @Positive(message = "La cantidad debe ser positiva")
        Integer cantidad
) {

}

