package com.christian.almacen.services.sucursales;

import com.christian.almacen.dto.sucursales.SucursalRequest;
import com.christian.almacen.dto.sucursales.SucursalResponse;
import com.christian.almacen.entities.Sucursal;
import com.christian.almacen.exceptions.RecursoNoEncontradoException;
import com.christian.almacen.mappers.SucursalMapper;
import com.christian.almacen.repositories.SucursalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class SucursalServiceImpl implements SucursalService {
    private final SucursalRepository sucursalRepository;
    private final SucursalMapper sucursalMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SucursalResponse> listar() {
        log.info("Listando todas las sucursales");

        return sucursalRepository.findAll().stream()
                .map(sucursalMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SucursalResponse obtenerPorId(Long id) {
        return sucursalMapper.entidadAResponse(obtenerSucursalOExcepcion(id));
    }

    @Override
    public SucursalResponse registrar(SucursalRequest request) {
        log.info("Registrando nueva sucursal");

        Sucursal sucursal = sucursalMapper.requestAEntidad(request);

        validarDatosUnicos(request);

        sucursalRepository.save(sucursal);

        log.info("Nueva sucursal registrada: {}", sucursal.getNombre());

        return sucursalMapper.entidadAResponse(sucursal);
    }

    @Override
    public SucursalResponse actualizar(SucursalRequest request, Long id) {
        Sucursal sucursal = obtenerSucursalOExcepcion(id);

        log.info("Actualizando sucursal con id: {}", id);

        validarCambiosUnicos(request, id);

        sucursal.actualizar(request.nombre(), request.direccion());

        log.info("Sucursal con id {} actualizada", id);

        return sucursalMapper.entidadAResponse(sucursal);
    }

    @Override
    public void eliminar(Long id) {
        Sucursal sucursal = obtenerSucursalOExcepcion(id);

        log.info("Eliminando sucursal con id: {}", id);

        sucursalRepository.delete(sucursal);

        log.info("Sucursal con id {} eliminada", id);
    }

    private Sucursal obtenerSucursalOExcepcion(Long id) {
        log.info("Buscando sucursal con id: {}", id);

        return sucursalRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(
                        "Sucursal no encontrada con id: " + id));
    }

    private void validarDatosUnicos(SucursalRequest request) {
        log.info("Validando nombre único...");

        if(sucursalRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new IllegalArgumentException("Ya existe una sucursal con el nombre de: " + request.nombre());
    }

    private void validarCambiosUnicos(SucursalRequest request, Long id) {
        log.info("Validando nombre único...");

        if(sucursalRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id))
            throw new IllegalArgumentException("Ya existe una sucursal con el nombre de: " + request.nombre());
    }
}
