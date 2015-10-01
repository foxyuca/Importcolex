package co.com.importcolex.tejeduria.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.com.importcolex.tejeduria.domain.DireccionamientoTela;
import co.com.importcolex.tejeduria.repository.DireccionamientoTelaRepository;
import co.com.importcolex.tejeduria.web.rest.util.HeaderUtil;
import co.com.importcolex.tejeduria.web.rest.util.PaginationUtil;
import co.com.importcolex.tejeduria.web.rest.dto.DireccionamientoTelaDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.DireccionamientoTelaMapper;
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
 * REST controller for managing DireccionamientoTela.
 */
@RestController
@RequestMapping("/api")
public class DireccionamientoTelaResource {

    private final Logger log = LoggerFactory.getLogger(DireccionamientoTelaResource.class);

    @Inject
    private DireccionamientoTelaRepository direccionamientoTelaRepository;

    @Inject
    private DireccionamientoTelaMapper direccionamientoTelaMapper;

    /**
     * POST  /direccionamientoTelas -> Create a new direccionamientoTela.
     */
    @RequestMapping(value = "/direccionamientoTelas",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DireccionamientoTelaDTO> createDireccionamientoTela(@Valid @RequestBody DireccionamientoTelaDTO direccionamientoTelaDTO) throws URISyntaxException {
        log.debug("REST request to save DireccionamientoTela : {}", direccionamientoTelaDTO);
        if (direccionamientoTelaDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new direccionamientoTela cannot already have an ID").body(null);
        }
        DireccionamientoTela direccionamientoTela = direccionamientoTelaMapper.direccionamientoTelaDTOToDireccionamientoTela(direccionamientoTelaDTO);
        DireccionamientoTela result = direccionamientoTelaRepository.save(direccionamientoTela);
        return ResponseEntity.created(new URI("/api/direccionamientoTelas/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("direccionamientoTela", result.getId().toString()))
                .body(direccionamientoTelaMapper.direccionamientoTelaToDireccionamientoTelaDTO(result));
    }

    /**
     * PUT  /direccionamientoTelas -> Updates an existing direccionamientoTela.
     */
    @RequestMapping(value = "/direccionamientoTelas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DireccionamientoTelaDTO> updateDireccionamientoTela(@Valid @RequestBody DireccionamientoTelaDTO direccionamientoTelaDTO) throws URISyntaxException {
        log.debug("REST request to update DireccionamientoTela : {}", direccionamientoTelaDTO);
        if (direccionamientoTelaDTO.getId() == null) {
            return createDireccionamientoTela(direccionamientoTelaDTO);
        }
        DireccionamientoTela direccionamientoTela = direccionamientoTelaMapper.direccionamientoTelaDTOToDireccionamientoTela(direccionamientoTelaDTO);
        DireccionamientoTela result = direccionamientoTelaRepository.save(direccionamientoTela);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("direccionamientoTela", direccionamientoTelaDTO.getId().toString()))
                .body(direccionamientoTelaMapper.direccionamientoTelaToDireccionamientoTelaDTO(result));
    }

    /**
     * GET  /direccionamientoTelas -> get all the direccionamientoTelas.
     */
    @RequestMapping(value = "/direccionamientoTelas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<DireccionamientoTelaDTO>> getAllDireccionamientoTelas(Pageable pageable)
        throws URISyntaxException {
        Page<DireccionamientoTela> page = direccionamientoTelaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/direccionamientoTelas");
        return new ResponseEntity<>(page.getContent().stream()
            .map(direccionamientoTelaMapper::direccionamientoTelaToDireccionamientoTelaDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /direccionamientoTelas/:id -> get the "id" direccionamientoTela.
     */
    @RequestMapping(value = "/direccionamientoTelas/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DireccionamientoTelaDTO> getDireccionamientoTela(@PathVariable Long id) {
        log.debug("REST request to get DireccionamientoTela : {}", id);
        return Optional.ofNullable(direccionamientoTelaRepository.findOne(id))
            .map(direccionamientoTelaMapper::direccionamientoTelaToDireccionamientoTelaDTO)
            .map(direccionamientoTelaDTO -> new ResponseEntity<>(
                direccionamientoTelaDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /direccionamientoTelas/:id -> delete the "id" direccionamientoTela.
     */
    @RequestMapping(value = "/direccionamientoTelas/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDireccionamientoTela(@PathVariable Long id) {
        log.debug("REST request to delete DireccionamientoTela : {}", id);
        direccionamientoTelaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("direccionamientoTela", id.toString())).build();
    }
}
