package co.com.importcolex.tejeduria.web.rest.mapper;

import co.com.importcolex.tejeduria.domain.*;
import co.com.importcolex.tejeduria.web.rest.dto.ClientesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Clientes and its DTO ClientesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientesMapper {

    ClientesDTO clientesToClientesDTO(Clientes clientes);

    @Mapping(target = "telaCrudas", ignore = true)
    Clientes clientesDTOToClientes(ClientesDTO clientesDTO);
}
