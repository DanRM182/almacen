package com.christian.almacen.dto;

public record CustomErrorResponse(
        int codigo,
        String mensaje) { }
