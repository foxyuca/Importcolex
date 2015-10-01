package co.com.importcolex.tejeduria.web.rest.mapper;

import co.com.importcolex.tejeduria.domain.*;
import co.com.importcolex.tejeduria.web.rest.dto.TipoIdentificacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoIdentificacion and its DTO TipoIdentificacionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoIdentificacionMapper {

    TipoIdentificacionDTO tipoIdentificacionToTipoIdentificacionDTO(TipoIdentificacion tipoIdentificacion);

    @Mapping(target = "proveedoress", ignore = true)
    TipoIdentificacion tipoIdentificacionDTOToTipoIdentificacion(TipoIdentificacionDTO tipoIdentificacionDTO);
}
