package com.christian.almacen.mappers;

import com.christian.almacen.dto.sucursales.SucursalResponse;
import com.christian.almacen.dto.ventas.VentaRequest;
import com.christian.almacen.dto.ventas.VentaResponse;
import com.christian.almacen.entities.Sucursal;
import com.christian.almacen.entities.Venta;
import com.christian.almacen.enums.EstadoVenta;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class VentaMapper {
    private final SucursalMapper sucursalMapper;
    private final DetalleVentaMapper detalleVentaMapper;

    public VentaMapper(SucursalMapper sucursalMapper, DetalleVentaMapper detalleVentaMapper) {
        this.sucursalMapper = sucursalMapper;
        this.detalleVentaMapper = detalleVentaMapper;
    }

    public Venta requestAEntidad(VentaRequest request, Sucursal sucursal) {
        if(request == null) return null;

        return Venta.builder()
                .estadoVenta(EstadoVenta.REGISTRADA)
                .fecha(LocalDate.now())
                .sucursal(sucursal)
                .build();
    }

    public VentaResponse entidadAResponse(Venta venta) {
        if(venta == null) return null;

        return new VentaResponse(
                venta.getId(),
                venta.getFecha().toString(),
                venta.getEstadoVenta().getDescripcion(),
                sucursalMapper.entidadAResponse(venta.getSucursal()),
                detalleVentaMapper.listaEntidadAResponse(venta.getDetalleVentas()),
                venta.totalVenta(venta.getDetalleVentas()));
    }
}
