package co.com.importcolex.tejeduria.web.rest.mapper;

import co.com.importcolex.tejeduria.domain.*;
import co.com.importcolex.tejeduria.web.rest.dto.InventarioFibrasDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InventarioFibras and its DTO InventarioFibrasDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InventarioFibrasMapper {

    @Mapping(source = "fibras.id", target = "fibrasId")
    @Mapping(source = "fibras.nombre", target = "fibrasNombre")
    @Mapping(source = "ordenCompra.id", target = "ordenCompraId")
    @Mapping(source = "ordenCompra.ticket", target = "ordenCompraTicket")
    InventarioFibrasDTO inventarioFibrasToInventarioFibrasDTO(InventarioFibras inventarioFibras);

    @Mapping(source = "fibrasId", target = "fibras")
    @Mapping(source = "ordenCompraId", target = "ordenCompra")
    @Mapping(target = "fibrasTelaCrudas", ignore = true)
    InventarioFibras inventarioFibrasDTOToInventarioFibras(InventarioFibrasDTO inventarioFibrasDTO);

    default Fibras fibrasFromId(Long id) {
        if (id == null) {
            return null;
        }
        Fibras fibras = new Fibras();
        fibras.setId(id);
        return fibras;
    }
    
    default OrdenCompra ordenCompraFromId(Long id) {
        if (id == null) {
            return null;
        }
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setId(id);
        return ordenCompra;
    }
}
