package co.com.importcolex.tejeduria.web.rest;

import com.codahale.metrics.annotation.Timed;

import co.com.importcolex.tejeduria.domain.OrdenCompra;
import co.com.importcolex.tejeduria.domain.util.CustomRandomGenerator;
import co.com.importcolex.tejeduria.repository.OrdenCompraRepository;
import co.com.importcolex.tejeduria.web.rest.util.HeaderUtil;
import co.com.importcolex.tejeduria.web.rest.util.PaginationUtil;
import co.com.importcolex.tejeduria.web.rest.dto.OrdenCompraDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.OrdenCompraMapper;

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

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing OrdenCompra.
 */
@RestController
@RequestMapping("/api")
public class OrdenCompraResource {

    private final Logger log = LoggerFactory.getLogger(OrdenCompraResource.class);

    @Inject
    private OrdenCompraRepository ordenCompraRepository;

    @Inject
    private OrdenCompraMapper ordenCompraMapper;

    @Inject
    public CustomRandomGenerator customRandomGenerator;

    
    /**
     * POST  /ordenCompras -> Create a new ordenCompra.
     */
    @RequestMapping(value = "/ordenCompras",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrdenCompraDTO> createOrdenCompra(@Valid @RequestBody OrdenCompraDTO ordenCompraDTO) throws URISyntaxException {
        log.debug("REST request to save OrdenCompra : {}", ordenCompraDTO);
        if (ordenCompraDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new ordenCompra cannot already have an ID").body(null);
        }
        OrdenCompra ordenCompra = ordenCompraMapper.ordenCompraDTOToOrdenCompra(ordenCompraDTO);
        ordenCompra.setTicket(new BigDecimal(customRandomGenerator.generateRandomTicket(99999999L)));
        OrdenCompra result = ordenCompraRepository.save(ordenCompra);
        return ResponseEntity.created(new URI("/api/ordenCompras/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("ordenCompra", result.getId().toString()))
                .body(ordenCompraMapper.ordenCompraToOrdenCompraDTO(result));
    }

    /**
     * PUT  /ordenCompras -> Updates an existing ordenCompra.
     */
    @RequestMapping(value = "/ordenCompras",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrdenCompraDTO> updateOrdenCompra(@Valid @RequestBody OrdenCompraDTO ordenCompraDTO) throws URISyntaxException {
        log.debug("REST request to update OrdenCompra : {}", ordenCompraDTO);
        if (ordenCompraDTO.getId() == null) {
            return createOrdenCompra(ordenCompraDTO);
        }
        OrdenCompra ordenCompra = ordenCompraMapper.ordenCompraDTOToOrdenCompra(ordenCompraDTO);
        OrdenCompra result = ordenCompraRepository.save(ordenCompra);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("ordenCompra", ordenCompraDTO.getId().toString()))
                .body(ordenCompraMapper.ordenCompraToOrdenCompraDTO(result));
    }

    /**
     * GET  /ordenCompras -> get all the ordenCompras.
     */
    @RequestMapping(value = "/ordenCompras",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<OrdenCompraDTO>> getAllOrdenCompras(Pageable pageable)
        throws URISyntaxException {
        Page<OrdenCompra> page = ordenCompraRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ordenCompras");
        return new ResponseEntity<>(page.getContent().stream()
            .map(ordenCompraMapper::ordenCompraToOrdenCompraDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /ordenCompras/:id -> get the "id" ordenCompra.
     */
    @RequestMapping(value = "/ordenCompras/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrdenCompraDTO> getOrdenCompra(@PathVariable Long id) {
        log.debug("REST request to get OrdenCompra : {}", id);
        return Optional.ofNullable(ordenCompraRepository.findOne(id))
            .map(ordenCompraMapper::ordenCompraToOrdenCompraDTO)
            .map(ordenCompraDTO -> new ResponseEntity<>(
                ordenCompraDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ordenCompras/:id -> delete the "id" ordenCompra.
     */
    @RequestMapping(value = "/ordenCompras/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOrdenCompra(@PathVariable Long id) {
        log.debug("REST request to delete OrdenCompra : {}", id);
        ordenCompraRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ordenCompra", id.toString())).build();
    }
    
}
