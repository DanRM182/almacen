package com.christian.almacen.controllers;

import com.christian.almacen.dto.ventas.VentaRequest;
import com.christian.almacen.dto.ventas.VentaResponse;
import com.christian.almacen.services.ventas.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@AllArgsConstructor
@Validated
@Tag(name = "Ventas", description = "Endpoints para la gestión de ventas realizadas")
public class VentaController {
    private final VentaService ventaService;

    @GetMapping
    @Operation(summary = "Listar ventas registradas", tags = {"Ventas - Consultas"})
    public ResponseEntity<List<VentaResponse>> listar() { return ResponseEntity.ok(ventaService.listar());}

    @GetMapping("/canceladas")
    @Operation(summary = "Listar ventas canceladas", tags = {"Ventas - Consultas"})
    public ResponseEntity<List<VentaResponse>> listarCanceladas() { return ResponseEntity.ok(ventaService.listarCanceladas());}

    @GetMapping("/{id}")
    @Operation(summary = "Buscar venta registrada", tags = {"Ventas - Consultas"})
    public ResponseEntity<VentaResponse> obtenerPorIdActiva(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ) { return ResponseEntity.ok(ventaService.obtenerPorIdActiva(id));}

    @PostMapping
    @Operation(summary = "Registrar nueva venta", tags = {"Ventas - Gestión"})
    public ResponseEntity<VentaResponse> registrar(
            @Valid @RequestBody VentaRequest request
            ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cancelar Venta", tags = {"Ventas - Gestión"})
    public ResponseEntity<VentaResponse> cancelar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ) {
        return ResponseEntity.ok(ventaService.cancelar(id));
    }
}
