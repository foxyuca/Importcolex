package co.com.importcolex.tejeduria.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.com.importcolex.tejeduria.domain.Fibras;
import co.com.importcolex.tejeduria.repository.FibrasRepository;
import co.com.importcolex.tejeduria.web.rest.util.HeaderUtil;
import co.com.importcolex.tejeduria.web.rest.util.PaginationUtil;
import co.com.importcolex.tejeduria.web.rest.dto.FibrasDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.FibrasMapper;
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
 * REST controller for managing Fibras.
 */
@RestController
@RequestMapping("/api")
public class FibrasResource {

    private final Logger log = LoggerFactory.getLogger(FibrasResource.class);

    @Inject
    private FibrasRepository fibrasRepository;

    @Inject
    private FibrasMapper fibrasMapper;

    /**
     * POST  /fibrass -> Create a new fibras.
     */
    @RequestMapping(value = "/fibrass",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FibrasDTO> createFibras(@Valid @RequestBody FibrasDTO fibrasDTO) throws URISyntaxException {
        log.debug("REST request to save Fibras : {}", fibrasDTO);
        if (fibrasDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new fibras cannot already have an ID").body(null);
        }
        Fibras fibras = fibrasMapper.fibrasDTOToFibras(fibrasDTO);
        Fibras result = fibrasRepository.save(fibras);
        return ResponseEntity.created(new URI("/api/fibrass/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("fibras", result.getId().toString()))
                .body(fibrasMapper.fibrasToFibrasDTO(result));
    }

    /**
     * PUT  /fibrass -> Updates an existing fibras.
     */
    @RequestMapping(value = "/fibrass",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FibrasDTO> updateFibras(@Valid @RequestBody FibrasDTO fibrasDTO) throws URISyntaxException {
        log.debug("REST request to update Fibras : {}", fibrasDTO);
        if (fibrasDTO.getId() == null) {
            return createFibras(fibrasDTO);
        }
        Fibras fibras = fibrasMapper.fibrasDTOToFibras(fibrasDTO);
        Fibras result = fibrasRepository.save(fibras);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("fibras", fibrasDTO.getId().toString()))
                .body(fibrasMapper.fibrasToFibrasDTO(result));
    }

    /**
     * GET  /fibrass -> get all the fibrass.
     */
    @RequestMapping(value = "/fibrass",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<FibrasDTO>> getAllFibrass(Pageable pageable)
        throws URISyntaxException {
        Page<Fibras> page = fibrasRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fibrass");
        return new ResponseEntity<>(page.getContent().stream()
            .map(fibrasMapper::fibrasToFibrasDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /fibrass/:id -> get the "id" fibras.
     */
    @RequestMapping(value = "/fibrass/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FibrasDTO> getFibras(@PathVariable Long id) {
        log.debug("REST request to get Fibras : {}", id);
        return Optional.ofNullable(fibrasRepository.findOne(id))
            .map(fibrasMapper::fibrasToFibrasDTO)
            .map(fibrasDTO -> new ResponseEntity<>(
                fibrasDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fibrass/:id -> delete the "id" fibras.
     */
    @RequestMapping(value = "/fibrass/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFibras(@PathVariable Long id) {
        log.debug("REST request to delete Fibras : {}", id);
        fibrasRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fibras", id.toString())).build();
    }
}
