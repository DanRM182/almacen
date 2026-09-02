package com.christian.almacen.services.sucursales;

import com.christian.almacen.dto.sucursales.SucursalRequest;
import com.christian.almacen.dto.sucursales.SucursalResponse;

import java.util.List;

public interface SucursalService {
    List<SucursalResponse> listar(String nombre, String descripcion);

    SucursalResponse obtenerPorId(Long id);

    SucursalResponse registrar(SucursalRequest request);

    SucursalResponse actualizar(SucursalRequest request);

    void eliminar(Long id);
}
