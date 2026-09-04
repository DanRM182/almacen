package com.christian.almacen.services.productos;

import com.christian.almacen.dto.productos.ProductoRequest;
import com.christian.almacen.dto.productos.ProductoResponse;
import com.christian.almacen.entities.Producto;
import com.christian.almacen.enums.Categoria;
import com.christian.almacen.exceptions.BusquedaSinParametrosException;
import com.christian.almacen.exceptions.RangoPrecioInvalidoException;
import com.christian.almacen.exceptions.RecursoNoEncontradoException;
import com.christian.almacen.mappers.ProductoMapper;
import com.christian.almacen.repositories.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(String nombre, String categoria, BigDecimal precioMin, BigDecimal precioMax) {
        validarDatosBusqueda(nombre, categoria, precioMin, precioMax);
        validarRangoOException(precioMin, precioMax);

        log.info("Listando todos los productos que cumplen con el criterio de búsqueda");

        return productoRepository.buscarPorNombrePorCategoriaPorRangoDePrecio(
                        validarDato(nombre, str -> str.trim(), null),
                        validarDato(categoria, Categoria::obtenerCategoriaPorDescripcion, null),
                        precioMin, precioMax)
                .stream().map(productoMapper::entidadAResponse).toList();
    }

    @Override
    public ProductoResponse obtenerPorId(Long id) {
        return productoMapper.entidadAResponse(obtenerProductoOException(id));
    }

    @Override
    public ProductoResponse registrar(ProductoRequest request) {
        log.info("Registrando nuevo producto...");
        Categoria categoria = null;
        Producto producto = productoMapper.requestAEntidad(request,
                Categoria.obtenerCategoriaPorDescripcion(request.categoria()));

        productoRepository.save(producto);

        log.info("Nuevo producto {} registrado", producto.getNombre());

        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {
        Producto producto = obtenerProductoOException(id);

        log.info("Actualizando producto con id: {}", id);

        producto.actualizar(
                request.nombre(),
                Categoria.obtenerCategoriaPorDescripcion(request.categoria()),
                request.precio(),
                request.cantidad()
        );

        //productoRepository.save(producto); NO NECESARIO POR DIRTY CHECKING
        log.info("Producto con id {} actualizado", id);

        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = obtenerProductoOException(id);
        log.info("Eliminando producto con id: {}", id);

        productoRepository.delete(producto);
        log.info("Producto con id {} eliminado", id);
    }

    private Producto obtenerProductoOException(Long id) {
        log.info("Buscando producto con id: {}", id);

        return productoRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(
                        "Producto no encontrado con id: " + id));
    }

    private void validarDatosBusqueda(String nombre, String categoria, BigDecimal precioMin, BigDecimal precioMax) {
        if(nombre == null && categoria == null && precioMin == null && precioMax == null)
            throw new BusquedaSinParametrosException("Sin parámetros de búsqueda");
    }

    private void validarRangoOException(BigDecimal precioMin, BigDecimal precioMax) {
        if (precioMin != null && precioMax != null) {
            if (precioMax.compareTo(precioMin) <= 0)
                throw new RangoPrecioInvalidoException("El precio máximo " + precioMax + " debe ser mayor al precio mínimo " + precioMin);
        }
    }

    public static <T> T validarDato(String input, Function<String, T> transformacion, T valorPorDefecto) {
        return Optional.ofNullable(input)
                .filter(str -> !str.isBlank())
                .map(transformacion)
                .orElse(valorPorDefecto);
    }
}