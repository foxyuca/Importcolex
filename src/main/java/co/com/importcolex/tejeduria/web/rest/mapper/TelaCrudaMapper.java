package co.com.importcolex.tejeduria.web.rest.mapper;

import co.com.importcolex.tejeduria.domain.*;
import co.com.importcolex.tejeduria.web.rest.dto.TelaCrudaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TelaCruda and its DTO TelaCrudaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TelaCrudaMapper {

    @Mapping(source = "tipoTela.id", target = "tipoTelaId")
    @Mapping(source = "tipoTela.nombre", target = "tipoTelaNombre")
    @Mapping(source = "maquina.id", target = "maquinaId")
    @Mapping(source = "operario.id", target = "operarioId")
    @Mapping(source = "operario.nombre", target = "operarioNombre")
    @Mapping(source = "clientes.id", target = "clientesId")
    @Mapping(source = "clientes.nombre", target = "clientesNombre")
    TelaCrudaDTO telaCrudaToTelaCrudaDTO(TelaCruda telaCruda);

    @Mapping(target = "fibrasTelaCrudas", ignore = true)
    @Mapping(source = "tipoTelaId", target = "tipoTela")
    @Mapping(source = "maquinaId", target = "maquina")
    @Mapping(source = "operarioId", target = "operario")
    @Mapping(source = "clientesId", target = "clientes")
    TelaCruda telaCrudaDTOToTelaCruda(TelaCrudaDTO telaCrudaDTO);

    default TipoTela tipoTelaFromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoTela tipoTela = new TipoTela();
        tipoTela.setId(id);
        return tipoTela;
    }

    default Maquina maquinaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Maquina maquina = new Maquina();
        maquina.setId(id);
        return maquina;
    }

    default Operario operarioFromId(Long id) {
        if (id == null) {
            return null;
        }
        Operario operario = new Operario();
        operario.setId(id);
        return operario;
    }

    default Clientes clientesFromId(Long id) {
        if (id == null) {
            return null;
        }
        Clientes clientes = new Clientes();
        clientes.setId(id);
        return clientes;
    }
}
