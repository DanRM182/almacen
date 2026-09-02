package com.christian.almacen.enums;

import com.christian.almacen.exceptions.RecursoNoEncontradoException;
import com.christian.almacen.utils.StringCustomUtils;
import com.christian.almacen.utils.ValoresNumericosUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstadoVenta {
    REGISTRADA(1L, "Registrada"),
    CANCELADA(0L, "Cancelada");

    private final Long codigo;
    private final String descripcion;

    public static EstadoVenta obtenerEstadoVentaPorDescripcion(String descripcion) {
        StringCustomUtils.validarNoVacio(descripcion, "La descripción es requerida");

        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descripcion);

        for(EstadoVenta estadoVenta: values()){
            if(StringCustomUtils.quitarAcentos(estadoVenta.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return estadoVenta;
        }

        throw new RecursoNoEncontradoException("No existe un estado de venta con la descripcion: " + descripcion);
    }

    public static EstadoVenta obtenerVentaPorCodigo(Long codigo) {
        ValoresNumericosUtils.validarNumeroRequerido(codigo);

        for (EstadoVenta estadoVenta: values()) {
            if(estadoVenta.codigo.equals(codigo))
                return estadoVenta;
        }

        throw new RecursoNoEncontradoException("No existe un estado de venta con el código: " + codigo);
    }
}
