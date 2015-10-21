package co.com.importcolex.tejeduria.web.rest.mapper;

import co.com.importcolex.tejeduria.domain.*;
import co.com.importcolex.tejeduria.web.rest.dto.ProveedoresDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Proveedores and its DTO ProveedoresDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProveedoresMapper {

	@Mapping(source = "tipoIdentificacion.id", target = "tipoIdentificacionId")
	@Mapping(source = "tipoIdentificacion.nombre", target = "tipoIdentificacionNombre")
    ProveedoresDTO proveedoresToProveedoresDTO(Proveedores proveedores);

    @Mapping(target = "ordencompras", ignore = true)
    @Mapping(source = "tipoIdentificacionId", target = "tipoIdentificacion")
    Proveedores proveedoresDTOToProveedores(ProveedoresDTO proveedoresDTO);
    
    
    default TipoIdentificacion tipoIdentificacionFromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoIdentificacion tipoIdentificacion = new TipoIdentificacion();
        tipoIdentificacion.setId(id);
        return tipoIdentificacion;
    }
}
