package com.christian.almacen.dto.reporteVentasSucursal;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Reporte de ventas por sucursal")
public record ReporteVentasSucursalResponse(
        @Schema(description = "ID de la sucursal", example = "1")
        Long idSucursal,

        @Schema(description = "Nombre de la sucursal", example = "Sucursal Norte")
        String nombreSucursal,

        @Schema(description = "Total facturado", example = "31631465.00")
        BigDecimal totalFacturado,

        @Schema(description = "Total productos vendidos", example = "50")
        Integer totalVendidos
) { }