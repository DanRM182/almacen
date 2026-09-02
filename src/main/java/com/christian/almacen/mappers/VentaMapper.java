package com.christian.almacen.mappers;

import com.christian.almacen.dto.ventas.VentaRequest;
import com.christian.almacen.dto.ventas.VentaResponse;
import com.christian.almacen.entities.Venta;
import org.springframework.stereotype.Component;

@Component
public class VentaMapper {
    public Venta requestAEntidad(VentaRequest request) {
        if(request == null) return null;
        return null;
    }

    public VentaResponse entidadAResponse(Venta venta) {
        if(venta == null) return null;

        return null;
    }
}
