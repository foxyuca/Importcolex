package co.com.importcolex.tejeduria.web.rest.mapper;

import co.com.importcolex.tejeduria.domain.*;
import co.com.importcolex.tejeduria.web.rest.dto.FibrasTelaCrudaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FibrasTelaCruda and its DTO FibrasTelaCrudaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FibrasTelaCrudaMapper {

    @Mapping(source = "inventarioFibras.id", target = "inventarioFibrasId")
    @Mapping(source = "inventarioFibras.lote", target = "inventarioFibrasLote")
    @Mapping(source = "telaCruda.id", target = "telaCrudaId")
    FibrasTelaCrudaDTO fibrasTelaCrudaToFibrasTelaCrudaDTO(FibrasTelaCruda fibrasTelaCruda);

    @Mapping(source = "inventarioFibrasId", target = "inventarioFibras")
    @Mapping(source = "telaCrudaId", target = "telaCruda")
    FibrasTelaCruda fibrasTelaCrudaDTOToFibrasTelaCruda(FibrasTelaCrudaDTO fibrasTelaCrudaDTO);

    default InventarioFibras inventarioFibrasFromId(Long id) {
        if (id == null) {
            return null;
        }
        InventarioFibras inventarioFibras = new InventarioFibras();
        inventarioFibras.setId(id);
        return inventarioFibras;
    }

    default TelaCruda telaCrudaFromId(Long id) {
        if (id == null) {
            return null;
        }
        TelaCruda telaCruda = new TelaCruda();
        telaCruda.setId(id);
        return telaCruda;
    }
}
