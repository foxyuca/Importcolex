package co.com.importcolex.tejeduria.web.rest.mapper;

import co.com.importcolex.tejeduria.domain.*;
import co.com.importcolex.tejeduria.web.rest.dto.TipoTelaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoTela and its DTO TipoTelaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoTelaMapper {

    @Mapping(source = "direccionamientoTela.id", target = "direccionamientoTelaId")
    @Mapping(source = "direccionamientoTela.nombre", target = "direccionamientoTelaNombre")
    TipoTelaDTO tipoTelaToTipoTelaDTO(TipoTela tipoTela);

    @Mapping(source = "direccionamientoTelaId", target = "direccionamientoTela")
    @Mapping(target = "telaCrudas", ignore = true)
    TipoTela tipoTelaDTOToTipoTela(TipoTelaDTO tipoTelaDTO);

    default DireccionamientoTela direccionamientoTelaFromId(Long id) {
        if (id == null) {
            return null;
        }
        DireccionamientoTela direccionamientoTela = new DireccionamientoTela();
        direccionamientoTela.setId(id);
        return direccionamientoTela;
    }
}
