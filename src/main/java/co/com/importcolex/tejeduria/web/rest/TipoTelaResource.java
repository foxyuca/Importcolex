package co.com.importcolex.tejeduria.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.com.importcolex.tejeduria.domain.TipoTela;
import co.com.importcolex.tejeduria.repository.TipoTelaRepository;
import co.com.importcolex.tejeduria.web.rest.util.HeaderUtil;
import co.com.importcolex.tejeduria.web.rest.util.PaginationUtil;
import co.com.importcolex.tejeduria.web.rest.dto.TipoTelaDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.TipoTelaMapper;
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
 * REST controller for managing TipoTela.
 */
@RestController
@RequestMapping("/api")
public class TipoTelaResource {

    private final Logger log = LoggerFactory.getLogger(TipoTelaResource.class);

    @Inject
    private TipoTelaRepository tipoTelaRepository;

    @Inject
    private TipoTelaMapper tipoTelaMapper;

    /**
     * POST  /tipoTelas -> Create a new tipoTela.
     */
    @RequestMapping(value = "/tipoTelas",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TipoTelaDTO> createTipoTela(@Valid @RequestBody TipoTelaDTO tipoTelaDTO) throws URISyntaxException {
        log.debug("REST request to save TipoTela : {}", tipoTelaDTO);
        if (tipoTelaDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new tipoTela cannot already have an ID").body(null);
        }
        TipoTela tipoTela = tipoTelaMapper.tipoTelaDTOToTipoTela(tipoTelaDTO);
        TipoTela result = tipoTelaRepository.save(tipoTela);
        return ResponseEntity.created(new URI("/api/tipoTelas/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("tipoTela", result.getId().toString()))
                .body(tipoTelaMapper.tipoTelaToTipoTelaDTO(result));
    }

    /**
     * PUT  /tipoTelas -> Updates an existing tipoTela.
     */
    @RequestMapping(value = "/tipoTelas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TipoTelaDTO> updateTipoTela(@Valid @RequestBody TipoTelaDTO tipoTelaDTO) throws URISyntaxException {
        log.debug("REST request to update TipoTela : {}", tipoTelaDTO);
        if (tipoTelaDTO.getId() == null) {
            return createTipoTela(tipoTelaDTO);
        }
        TipoTela tipoTela = tipoTelaMapper.tipoTelaDTOToTipoTela(tipoTelaDTO);
        TipoTela result = tipoTelaRepository.save(tipoTela);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("tipoTela", tipoTelaDTO.getId().toString()))
                .body(tipoTelaMapper.tipoTelaToTipoTelaDTO(result));
    }

    /**
     * GET  /tipoTelas -> get all the tipoTelas.
     */
    @RequestMapping(value = "/tipoTelas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<TipoTelaDTO>> getAllTipoTelas(Pageable pageable)
        throws URISyntaxException {
        Page<TipoTela> page = tipoTelaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipoTelas");
        return new ResponseEntity<>(page.getContent().stream()
            .map(tipoTelaMapper::tipoTelaToTipoTelaDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipoTelas/:id -> get the "id" tipoTela.
     */
    @RequestMapping(value = "/tipoTelas/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TipoTelaDTO> getTipoTela(@PathVariable Long id) {
        log.debug("REST request to get TipoTela : {}", id);
        return Optional.ofNullable(tipoTelaRepository.findOne(id))
            .map(tipoTelaMapper::tipoTelaToTipoTelaDTO)
            .map(tipoTelaDTO -> new ResponseEntity<>(
                tipoTelaDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tipoTelas/:id -> delete the "id" tipoTela.
     */
    @RequestMapping(value = "/tipoTelas/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTipoTela(@PathVariable Long id) {
        log.debug("REST request to delete TipoTela : {}", id);
        tipoTelaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tipoTela", id.toString())).build();
    }
}
