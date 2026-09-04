package com.christian.almacen.services.ventas;

import com.christian.almacen.dto.reporteVentasSucursal.ReporteVentasSucursalResponse;
import com.christian.almacen.dto.ventas.DetalleVentaRequest;
import com.christian.almacen.dto.ventas.VentaRequest;
import com.christian.almacen.dto.ventas.VentaResponse;
import com.christian.almacen.entities.DetalleVenta;
import com.christian.almacen.entities.Producto;
import com.christian.almacen.entities.Sucursal;
import com.christian.almacen.entities.Venta;
import com.christian.almacen.enums.EstadoVenta;
import com.christian.almacen.exceptions.RecursoNoEncontradoException;
import com.christian.almacen.exceptions.VentaNoEncontradaONoActivaException;
import com.christian.almacen.mappers.DetalleVentaMapper;
import com.christian.almacen.mappers.VentaMapper;
import com.christian.almacen.repositories.ProductoRepository;
import com.christian.almacen.repositories.SucursalRepository;
import com.christian.almacen.repositories.VentaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class VentaServiceImpl implements VentaService {
    private final VentaRepository ventaRepository;
    private final SucursalRepository sucursalRepository;
    private final ProductoRepository productoRepository;
    private final VentaMapper ventaMapper;
    private final DetalleVentaMapper detalleVentaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listar() {
        log.info("Listando ventas registradas");

        return obtenerListas(EstadoVenta.REGISTRADA);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listarCanceladas() {
        log.info("Listando ventas canceladas");

        return obtenerListas(EstadoVenta.CANCELADA);
    }

    @Override
    public VentaResponse obtenerPorIdActiva(Long id) {
        log.info("Buscando venta activa con id: {}", id);
        return ventaRepository.findByIdAndEstadoVenta(id, EstadoVenta.REGISTRADA)
                .map(ventaMapper::entidadAResponse)
                .orElseThrow(
                        () -> new VentaNoEncontradaONoActivaException(
                                "Venta con id: " + id + " no encontrada o no está activa"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public VentaResponse registrar(VentaRequest request) {
        validarSucursalOException(request.idSucursal());

        log.info("Registrando nueva venta...");

        Sucursal sucursal = sucursalRepository.getReferenceById(request.idSucursal());

        Venta venta = ventaMapper.requestAEntidad(request, sucursal);

        venta = registrarDetalleVenta(venta, request);
        log.info("Detalle Venta agregado: {}", venta.getSucursal().getNombre());
        ventaRepository.save(venta);

        log.info("Venta registrada correctamente...");

        return  ventaMapper.entidadAResponse(venta);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public VentaResponse cancelar(Long id) {
        Venta venta = obtenerVentaOExcepcion(id);

        log.info("Cancelando venta con id: {}", id);

        venta.cancelar();

        regresarProductos(venta);

        venta = ventaRepository.save(venta);

        log.info("Venta cancelada correctamente");

        return ventaMapper.entidadAResponse(venta);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReporteVentasSucursalResponse> generarReporteVentasGeneral() {
        log.info("Generando reporte de ventas activas por sucursal");

        return ventaRepository.generarReporteVentasPorSucursal(EstadoVenta.REGISTRADA);
    }

    private void validarSucursalOException(Long id) {
        log.info("Validando que el id {} de sucursal exista", id);

        if(!sucursalRepository.existsById(id))
            throw new RecursoNoEncontradoException("Sucursal con id: " + id + " no encontrada");
    }

    private Venta registrarDetalleVenta(Venta venta, VentaRequest request) {
        for(DetalleVentaRequest productoVendido : request.productos()) {
            Producto producto = productoRepository.findById(productoVendido.idProducto())
                    .orElseThrow(
                            () -> new RecursoNoEncontradoException("Producto con id: " + productoVendido.idProducto() + " no encontrado"));

            producto.descontarCantidad(productoVendido.cantidadProducto());

            DetalleVenta detalleVenta = detalleVentaMapper.requestAEntidad(productoVendido, producto);

            venta.agregarDetalle(detalleVenta);
        }
        return venta;
    }

    private Venta obtenerVentaOExcepcion(Long id) {
        log.info("Buscando venta con id: {}", id);

        return ventaRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(
                        "Venta con id: " + id + " no encontrada"));
    }

    private void regresarProductos(Venta venta) {
        for(DetalleVenta detalle: venta.getDetalleVentas()) {
            Producto producto = detalle.getProducto();

            producto.aumentarCantidad(detalle.getCantidadProducto());

            productoRepository.save(producto);
        }
    }

    private List<VentaResponse> obtenerListas(EstadoVenta estadoVenta) {
        List<Venta> ventas = ventaRepository.findByEstadoVenta(estadoVenta);

        return ventas.stream()
                .map(ventaMapper::entidadAResponse)
                .toList();
    }
}
