package com.christian.almacen.dto.sucursales;

import io.swagger.v3.oas.annotations.media.Schema;

public record SucursalResponse(
        @Schema(description = "Identificador de una sucursal", example = "1")
        Long id,

        @Schema(description = "Nombre de la sucursal", example = "Steren S.A. de C.V.")
        String nombre,

        @Schema(description = "Descripción de la sucursal", example="Steren siempre a la vanguardia de la tecnología con los mejores productos para ti")
        String descripcion
) {
}
