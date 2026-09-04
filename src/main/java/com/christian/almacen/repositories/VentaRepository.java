package com.christian.almacen.repositories;

import com.christian.almacen.dto.reporteVentasSucursal.ReporteVentasSucursalResponse;
import com.christian.almacen.entities.Venta;
import com.christian.almacen.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByEstadoVenta(EstadoVenta estadoVenta);

    Optional<Venta> findByIdAndEstadoVenta(Long id, EstadoVenta estadoVenta);

    @Query("""
            SELECT new com.christian.almacen.dto.reporteVentasSucursal.ReporteVentasSucursalResponse(
              CAST( d.venta.sucursal.id AS long),
              d.venta.sucursal.nombre,
              CAST(SUM(d.cantidadProducto * d.precioProducto) AS java.math.BigDecimal),
              CAST(SUM(d.cantidadProducto) AS INTEGER)
            ) 
            FROM DetalleVenta d 
            WHERE d.venta.estadoVenta = :estadoVenta 
            GROUP BY d.venta.sucursal.id, d.venta.sucursal.nombre""")
    List<ReporteVentasSucursalResponse> generarReporteVentasPorSucursal(
            @Param("estadoVenta") EstadoVenta estadoVenta
    );
}
