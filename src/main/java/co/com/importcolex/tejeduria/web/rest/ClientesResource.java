package co.com.importcolex.tejeduria.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.com.importcolex.tejeduria.domain.Clientes;
import co.com.importcolex.tejeduria.repository.ClientesRepository;
import co.com.importcolex.tejeduria.web.rest.util.HeaderUtil;
import co.com.importcolex.tejeduria.web.rest.util.PaginationUtil;
import co.com.importcolex.tejeduria.web.rest.dto.ClientesDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.ClientesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Clientes.
 */
@RestController
@RequestMapping("/api")
public class ClientesResource {

    private final Logger log = LoggerFactory.getLogger(ClientesResource.class);

    @Inject
    private ClientesRepository clientesRepository;

    @Inject
    private ClientesMapper clientesMapper;

    /**
     * POST  /clientess -> Create a new clientes.
     */
    @RequestMapping(value = "/clientess",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClientesDTO> createClientes(@Valid @RequestBody ClientesDTO clientesDTO) throws URISyntaxException {
        log.debug("REST request to save Clientes : {}", clientesDTO);
        if (clientesDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new clientes cannot already have an ID").body(null);
        }
        Clientes clientes = clientesMapper.clientesDTOToClientes(clientesDTO);
        Clientes result = clientesRepository.save(clientes);
        return ResponseEntity.created(new URI("/api/clientess/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("clientes", result.getId().toString()))
                .body(clientesMapper.clientesToClientesDTO(result));
    }

    /**
     * PUT  /clientess -> Updates an existing clientes.
     */
    @RequestMapping(value = "/clientess",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClientesDTO> updateClientes(@Valid @RequestBody ClientesDTO clientesDTO) throws URISyntaxException {
        log.debug("REST request to update Clientes : {}", clientesDTO);
        if (clientesDTO.getId() == null) {
            return createClientes(clientesDTO);
        }
        Clientes clientes = clientesMapper.clientesDTOToClientes(clientesDTO);
        Clientes result = clientesRepository.save(clientes);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("clientes", clientesDTO.getId().toString()))
                .body(clientesMapper.clientesToClientesDTO(result));
    }

    /**
     * GET  /clientess -> get all the clientess.
     */
    @RequestMapping(value = "/clientess",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ClientesDTO>> getAllClientess(Pageable pageable)
        throws URISyntaxException {
        Page<Clientes> page = clientesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clientess");
        return new ResponseEntity<>(page.getContent().stream()
            .map(clientesMapper::clientesToClientesDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /clientess/:id -> get the "id" clientes.
     */
    @RequestMapping(value = "/clientess/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClientesDTO> getClientes(@PathVariable Long id) {
        log.debug("REST request to get Clientes : {}", id);
        return Optional.ofNullable(clientesRepository.findOne(id))
            .map(clientesMapper::clientesToClientesDTO)
            .map(clientesDTO -> new ResponseEntity<>(
                clientesDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /clientess/:id -> delete the "id" clientes.
     */
    @RequestMapping(value = "/clientess/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteClientes(@PathVariable Long id) {
        log.debug("REST request to delete Clientes : {}", id);
        clientesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("clientes", id.toString())).build();
    }
}
