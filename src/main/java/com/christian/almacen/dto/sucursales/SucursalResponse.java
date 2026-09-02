package com.christian.almacen.dto.sucursales;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información de una sucursal")
public record SucursalResponse(
        @Schema(description = "Identificador de una sucursal", example = "1")
        Long id,

        @Schema(description = "Nombre de la sucursal", example = "Sucursal norte")
        String nombre,

        @Schema(description = "Dirección de la sucursal", example="Av. Adolfo Lopez Mateos 1501")
        String direccion
) { }
