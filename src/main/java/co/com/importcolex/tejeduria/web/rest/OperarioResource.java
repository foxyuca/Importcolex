package co.com.importcolex.tejeduria.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.com.importcolex.tejeduria.domain.Operario;
import co.com.importcolex.tejeduria.repository.OperarioRepository;
import co.com.importcolex.tejeduria.web.rest.util.HeaderUtil;
import co.com.importcolex.tejeduria.web.rest.util.PaginationUtil;
import co.com.importcolex.tejeduria.web.rest.dto.OperarioDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.OperarioMapper;
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
 * REST controller for managing Operario.
 */
@RestController
@RequestMapping("/api")
public class OperarioResource {

    private final Logger log = LoggerFactory.getLogger(OperarioResource.class);

    @Inject
    private OperarioRepository operarioRepository;

    @Inject
    private OperarioMapper operarioMapper;

    /**
     * POST  /operarios -> Create a new operario.
     */
    @RequestMapping(value = "/operarios",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OperarioDTO> createOperario(@Valid @RequestBody OperarioDTO operarioDTO) throws URISyntaxException {
        log.debug("REST request to save Operario : {}", operarioDTO);
        if (operarioDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new operario cannot already have an ID").body(null);
        }
        Operario operario = operarioMapper.operarioDTOToOperario(operarioDTO);
        Operario result = operarioRepository.save(operario);
        return ResponseEntity.created(new URI("/api/operarios/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("operario", result.getId().toString()))
                .body(operarioMapper.operarioToOperarioDTO(result));
    }

    /**
     * PUT  /operarios -> Updates an existing operario.
     */
    @RequestMapping(value = "/operarios",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OperarioDTO> updateOperario(@Valid @RequestBody OperarioDTO operarioDTO) throws URISyntaxException {
        log.debug("REST request to update Operario : {}", operarioDTO);
        if (operarioDTO.getId() == null) {
            return createOperario(operarioDTO);
        }
        Operario operario = operarioMapper.operarioDTOToOperario(operarioDTO);
        Operario result = operarioRepository.save(operario);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("operario", operarioDTO.getId().toString()))
                .body(operarioMapper.operarioToOperarioDTO(result));
    }

    /**
     * GET  /operarios -> get all the operarios.
     */
    @RequestMapping(value = "/operarios",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<OperarioDTO>> getAllOperarios(Pageable pageable)
        throws URISyntaxException {
        Page<Operario> page = operarioRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operarios");
        return new ResponseEntity<>(page.getContent().stream()
            .map(operarioMapper::operarioToOperarioDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /operarios/:id -> get the "id" operario.
     */
    @RequestMapping(value = "/operarios/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OperarioDTO> getOperario(@PathVariable Long id) {
        log.debug("REST request to get Operario : {}", id);
        return Optional.ofNullable(operarioRepository.findOne(id))
            .map(operarioMapper::operarioToOperarioDTO)
            .map(operarioDTO -> new ResponseEntity<>(
                operarioDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /operarios/:id -> delete the "id" operario.
     */
    @RequestMapping(value = "/operarios/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOperario(@PathVariable Long id) {
        log.debug("REST request to delete Operario : {}", id);
        operarioRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("operario", id.toString())).build();
    }
}
