package co.com.importcolex.tejeduria.web.rest.mapper;

import co.com.importcolex.tejeduria.domain.*;
import co.com.importcolex.tejeduria.web.rest.dto.ProveedoresDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Proveedores and its DTO ProveedoresDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProveedoresMapper {

    ProveedoresDTO proveedoresToProveedoresDTO(Proveedores proveedores);

    @Mapping(target = "ordencompras", ignore = true)
    Proveedores proveedoresDTOToProveedores(ProveedoresDTO proveedoresDTO);
}
