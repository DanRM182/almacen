package com.christian.almacen.dto.sucursales;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SucursalRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 50, message = "El nombre debe tener entre 5 a 50 caracteres")
        @Schema(description = "Nombre de la sucursal", example = "Steren S.A. de C.V.")
        String nombre,

        @NotBlank(message = "La descripción es requerida")
        @Size(min = 5, max = 150, message = "La descripción debe tener entre 5 a 150 caracteres")
        @Schema(description = "Descripción de la sucursal", example="Steren siempre a la vanguardia de la tecnología con los mejores productos para ti")
        String descripcion
) {
}
