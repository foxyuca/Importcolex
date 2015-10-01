package co.com.importcolex.tejeduria.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.com.importcolex.tejeduria.domain.InventarioFibras;
import co.com.importcolex.tejeduria.repository.InventarioFibrasRepository;
import co.com.importcolex.tejeduria.web.rest.util.HeaderUtil;
import co.com.importcolex.tejeduria.web.rest.util.PaginationUtil;
import co.com.importcolex.tejeduria.web.rest.dto.InventarioFibrasDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.InventarioFibrasMapper;
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
 * REST controller for managing InventarioFibras.
 */
@RestController
@RequestMapping("/api")
public class InventarioFibrasResource {

    private final Logger log = LoggerFactory.getLogger(InventarioFibrasResource.class);

    @Inject
    private InventarioFibrasRepository inventarioFibrasRepository;

    @Inject
    private InventarioFibrasMapper inventarioFibrasMapper;

    /**
     * POST  /inventarioFibrass -> Create a new inventarioFibras.
     */
    @RequestMapping(value = "/inventarioFibrass",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InventarioFibrasDTO> createInventarioFibras(@Valid @RequestBody InventarioFibrasDTO inventarioFibrasDTO) throws URISyntaxException {
        log.debug("REST request to save InventarioFibras : {}", inventarioFibrasDTO);
        if (inventarioFibrasDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new inventarioFibras cannot already have an ID").body(null);
        }
        InventarioFibras inventarioFibras = inventarioFibrasMapper.inventarioFibrasDTOToInventarioFibras(inventarioFibrasDTO);
        InventarioFibras result = inventarioFibrasRepository.save(inventarioFibras);
        return ResponseEntity.created(new URI("/api/inventarioFibrass/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("inventarioFibras", result.getId().toString()))
                .body(inventarioFibrasMapper.inventarioFibrasToInventarioFibrasDTO(result));
    }

    /**
     * PUT  /inventarioFibrass -> Updates an existing inventarioFibras.
     */
    @RequestMapping(value = "/inventarioFibrass",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InventarioFibrasDTO> updateInventarioFibras(@Valid @RequestBody InventarioFibrasDTO inventarioFibrasDTO) throws URISyntaxException {
        log.debug("REST request to update InventarioFibras : {}", inventarioFibrasDTO);
        if (inventarioFibrasDTO.getId() == null) {
            return createInventarioFibras(inventarioFibrasDTO);
        }
        InventarioFibras inventarioFibras = inventarioFibrasMapper.inventarioFibrasDTOToInventarioFibras(inventarioFibrasDTO);
        InventarioFibras result = inventarioFibrasRepository.save(inventarioFibras);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("inventarioFibras", inventarioFibrasDTO.getId().toString()))
                .body(inventarioFibrasMapper.inventarioFibrasToInventarioFibrasDTO(result));
    }

    /**
     * GET  /inventarioFibrass -> get all the inventarioFibrass.
     */
    @RequestMapping(value = "/inventarioFibrass",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<InventarioFibrasDTO>> getAllInventarioFibrass(Pageable pageable)
        throws URISyntaxException {
        Page<InventarioFibras> page = inventarioFibrasRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inventarioFibrass");
        return new ResponseEntity<>(page.getContent().stream()
            .map(inventarioFibrasMapper::inventarioFibrasToInventarioFibrasDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /inventarioFibrass/:id -> get the "id" inventarioFibras.
     */
    @RequestMapping(value = "/inventarioFibrass/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InventarioFibrasDTO> getInventarioFibras(@PathVariable Long id) {
        log.debug("REST request to get InventarioFibras : {}", id);
        return Optional.ofNullable(inventarioFibrasRepository.findOne(id))
            .map(inventarioFibrasMapper::inventarioFibrasToInventarioFibrasDTO)
            .map(inventarioFibrasDTO -> new ResponseEntity<>(
                inventarioFibrasDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /inventarioFibrass/:id -> delete the "id" inventarioFibras.
     */
    @RequestMapping(value = "/inventarioFibrass/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInventarioFibras(@PathVariable Long id) {
        log.debug("REST request to delete InventarioFibras : {}", id);
        inventarioFibrasRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("inventarioFibras", id.toString())).build();
    }
}
