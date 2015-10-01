package co.com.importcolex.tejeduria.web.rest.mapper;

import co.com.importcolex.tejeduria.domain.*;
import co.com.importcolex.tejeduria.web.rest.dto.MaquinaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Maquina and its DTO MaquinaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MaquinaMapper {

    MaquinaDTO maquinaToMaquinaDTO(Maquina maquina);

    @Mapping(target = "telaCrudas", ignore = true)
    Maquina maquinaDTOToMaquina(MaquinaDTO maquinaDTO);
}
