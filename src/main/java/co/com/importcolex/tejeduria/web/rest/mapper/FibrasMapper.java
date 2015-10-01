package co.com.importcolex.tejeduria.web.rest.mapper;

import co.com.importcolex.tejeduria.domain.*;
import co.com.importcolex.tejeduria.web.rest.dto.FibrasDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Fibras and its DTO FibrasDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FibrasMapper {

    FibrasDTO fibrasToFibrasDTO(Fibras fibras);

    @Mapping(target = "ordenCompras", ignore = true)
    @Mapping(target = "inventarioFibrass", ignore = true)
    Fibras fibrasDTOToFibras(FibrasDTO fibrasDTO);
}
