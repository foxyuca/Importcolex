package co.com.importcolex.tejeduria.web.rest.mapper;

import co.com.importcolex.tejeduria.domain.*;
import co.com.importcolex.tejeduria.web.rest.dto.OrdenCompraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrdenCompra and its DTO OrdenCompraDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdenCompraMapper {

    @Mapping(source = "proveedores.id", target = "proveedoresId")
    @Mapping(source = "proveedores.nombre", target = "proveedoresNombre")
    @Mapping(source = "fibras.id", target = "fibrasId")
    @Mapping(source = "fibras.nombre", target = "fibrasNombre")
    OrdenCompraDTO ordenCompraToOrdenCompraDTO(OrdenCompra ordenCompra);

    @Mapping(source = "proveedoresId", target = "proveedores")
    @Mapping(source = "fibrasId", target = "fibras")
    @Mapping(target = "inventarioFibrass", ignore = true)
    OrdenCompra ordenCompraDTOToOrdenCompra(OrdenCompraDTO ordenCompraDTO);

    default Proveedores proveedoresFromId(Long id) {
        if (id == null) {
            return null;
        }
        Proveedores proveedores = new Proveedores();
        proveedores.setId(id);
        return proveedores;
    }

    default Fibras fibrasFromId(Long id) {
        if (id == null) {
            return null;
        }
        Fibras fibras = new Fibras();
        fibras.setId(id);
        return fibras;
    }
}
