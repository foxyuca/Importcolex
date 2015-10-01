package co.com.importcolex.tejeduria.web.rest.mapper;

import co.com.importcolex.tejeduria.domain.*;
import co.com.importcolex.tejeduria.web.rest.dto.OperarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Operario and its DTO OperarioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OperarioMapper {

    OperarioDTO operarioToOperarioDTO(Operario operario);

    @Mapping(target = "telaCrudas", ignore = true)
    Operario operarioDTOToOperario(OperarioDTO operarioDTO);
}
