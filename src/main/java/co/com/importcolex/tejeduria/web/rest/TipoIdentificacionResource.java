package co.com.importcolex.tejeduria.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.com.importcolex.tejeduria.domain.TipoIdentificacion;
import co.com.importcolex.tejeduria.repository.TipoIdentificacionRepository;
import co.com.importcolex.tejeduria.web.rest.util.HeaderUtil;
import co.com.importcolex.tejeduria.web.rest.util.PaginationUtil;
import co.com.importcolex.tejeduria.web.rest.dto.TipoIdentificacionDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.TipoIdentificacionMapper;
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
 * REST controller for managing TipoIdentificacion.
 */
@RestController
@RequestMapping("/api")
public class TipoIdentificacionResource {

    private final Logger log = LoggerFactory.getLogger(TipoIdentificacionResource.class);

    @Inject
    private TipoIdentificacionRepository tipoIdentificacionRepository;

    @Inject
    private TipoIdentificacionMapper tipoIdentificacionMapper;

    /**
     * POST  /tipoIdentificacions -> Create a new tipoIdentificacion.
     */
    @RequestMapping(value = "/tipoIdentificacions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TipoIdentificacionDTO> createTipoIdentificacion(@Valid @RequestBody TipoIdentificacionDTO tipoIdentificacionDTO) throws URISyntaxException {
        log.debug("REST request to save TipoIdentificacion : {}", tipoIdentificacionDTO);
        if (tipoIdentificacionDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new tipoIdentificacion cannot already have an ID").body(null);
        }
        TipoIdentificacion tipoIdentificacion = tipoIdentificacionMapper.tipoIdentificacionDTOToTipoIdentificacion(tipoIdentificacionDTO);
        TipoIdentificacion result = tipoIdentificacionRepository.save(tipoIdentificacion);
        return ResponseEntity.created(new URI("/api/tipoIdentificacions/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("tipoIdentificacion", result.getId().toString()))
                .body(tipoIdentificacionMapper.tipoIdentificacionToTipoIdentificacionDTO(result));
    }

    /**
     * PUT  /tipoIdentificacions -> Updates an existing tipoIdentificacion.
     */
    @RequestMapping(value = "/tipoIdentificacions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TipoIdentificacionDTO> updateTipoIdentificacion(@Valid @RequestBody TipoIdentificacionDTO tipoIdentificacionDTO) throws URISyntaxException {
        log.debug("REST request to update TipoIdentificacion : {}", tipoIdentificacionDTO);
        if (tipoIdentificacionDTO.getId() == null) {
            return createTipoIdentificacion(tipoIdentificacionDTO);
        }
        TipoIdentificacion tipoIdentificacion = tipoIdentificacionMapper.tipoIdentificacionDTOToTipoIdentificacion(tipoIdentificacionDTO);
        TipoIdentificacion result = tipoIdentificacionRepository.save(tipoIdentificacion);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("tipoIdentificacion", tipoIdentificacionDTO.getId().toString()))
                .body(tipoIdentificacionMapper.tipoIdentificacionToTipoIdentificacionDTO(result));
    }

    /**
     * GET  /tipoIdentificacions -> get all the tipoIdentificacions.
     */
    @RequestMapping(value = "/tipoIdentificacions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<TipoIdentificacionDTO>> getAllTipoIdentificacions(Pageable pageable)
        throws URISyntaxException {
        Page<TipoIdentificacion> page = tipoIdentificacionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipoIdentificacions");
        return new ResponseEntity<>(page.getContent().stream()
            .map(tipoIdentificacionMapper::tipoIdentificacionToTipoIdentificacionDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipoIdentificacions/:id -> get the "id" tipoIdentificacion.
     */
    @RequestMapping(value = "/tipoIdentificacions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TipoIdentificacionDTO> getTipoIdentificacion(@PathVariable Long id) {
        log.debug("REST request to get TipoIdentificacion : {}", id);
        return Optional.ofNullable(tipoIdentificacionRepository.findOne(id))
            .map(tipoIdentificacionMapper::tipoIdentificacionToTipoIdentificacionDTO)
            .map(tipoIdentificacionDTO -> new ResponseEntity<>(
                tipoIdentificacionDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tipoIdentificacions/:id -> delete the "id" tipoIdentificacion.
     */
    @RequestMapping(value = "/tipoIdentificacions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTipoIdentificacion(@PathVariable Long id) {
        log.debug("REST request to delete TipoIdentificacion : {}", id);
        tipoIdentificacionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tipoIdentificacion", id.toString())).build();
    }
}
