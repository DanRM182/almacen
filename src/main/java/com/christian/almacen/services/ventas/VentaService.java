package com.christian.almacen.services.ventas;

import com.christian.almacen.dto.reporteVentasSucursal.ReporteVentasSucursalResponse;
import com.christian.almacen.dto.ventas.VentaRequest;
import com.christian.almacen.dto.ventas.VentaResponse;

import java.util.List;

public interface VentaService {
    List<VentaResponse> listar();

    List<VentaResponse> listarCanceladas();

    List<ReporteVentasSucursalResponse> generarReporteVentasGeneral();

    VentaResponse obtenerPorIdActiva(Long id);

    VentaResponse registrar(VentaRequest request);

    VentaResponse cancelar(Long id);
}
