package com.christian.almacen.mappers;

import com.christian.almacen.dto.ventas.DetalleVentaRequest;
import com.christian.almacen.dto.ventas.DetalleVentaResponse;
import com.christian.almacen.entities.DetalleVenta;
import com.christian.almacen.entities.Producto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Component
public class DetalleVentaMapper {
    public DetalleVenta requestAEntidad(DetalleVentaRequest request, Producto producto) {
        if(request == null) return null;

        return DetalleVenta.builder()
                .producto(producto)
                .cantidadProducto(request.cantidadProducto())
                .precioProducto(producto.getPrecio())
                .build();
    }

    public DetalleVentaResponse entidadAResponse(DetalleVenta detalleVenta) {
        if(detalleVenta == null) return null;

        return new DetalleVentaResponse(
                detalleVenta.getProducto().getId(),
                detalleVenta.getProducto().getNombre(),
                detalleVenta.getCantidadProducto(),
                detalleVenta.getPrecioProducto(),
                detalleVenta.getPrecioProducto().multiply(BigDecimal.valueOf(detalleVenta.getCantidadProducto())));
    }

    public List<DetalleVentaResponse> listaEntidadAResponse(List<DetalleVenta> detalleVentas) {
        if(detalleVentas == null || detalleVentas.isEmpty()) return null;

        return detalleVentas.stream()
                .map(this::entidadAResponse)
                .toList();
    }
}
