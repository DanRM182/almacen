package com.christian.almacen.dto.ventas;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "Detalle de un producto de la venta")
public record DetalleVentaRequest(
        @Schema(description = "ID del producto", example = "1")
        @NotNull(message = "El ID del producto es requerido")
        @Positive(message = "El ID del producto debe ser positivo")
        Long idProducto,

        @Schema(description = "Cantidad del producto", example = "100")
        @NotNull(message = "La cantidad del producto es requerida")
        @Positive(message = "La cantidad del producto debe ser positiva")
        Integer cantidadProducto,

        @Schema(description = "Precio unitario del producto", example = "99.99")
        @NotNull(message = "El precio del producto es requerido")
        @Positive(message = "El precio del producto debe ser positivo")
        BigDecimal precioProducto
) {
}
