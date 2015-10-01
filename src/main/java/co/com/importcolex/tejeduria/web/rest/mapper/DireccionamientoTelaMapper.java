package co.com.importcolex.tejeduria.web.rest.mapper;

import co.com.importcolex.tejeduria.domain.*;
import co.com.importcolex.tejeduria.web.rest.dto.DireccionamientoTelaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DireccionamientoTela and its DTO DireccionamientoTelaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DireccionamientoTelaMapper {

    DireccionamientoTelaDTO direccionamientoTelaToDireccionamientoTelaDTO(DireccionamientoTela direccionamientoTela);

    @Mapping(target = "tipoTelas", ignore = true)
    DireccionamientoTela direccionamientoTelaDTOToDireccionamientoTela(DireccionamientoTelaDTO direccionamientoTelaDTO);
}
