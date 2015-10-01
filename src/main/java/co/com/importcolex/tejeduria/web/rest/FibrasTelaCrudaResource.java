package co.com.importcolex.tejeduria.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.com.importcolex.tejeduria.domain.FibrasTelaCruda;
import co.com.importcolex.tejeduria.repository.FibrasTelaCrudaRepository;
import co.com.importcolex.tejeduria.web.rest.util.HeaderUtil;
import co.com.importcolex.tejeduria.web.rest.util.PaginationUtil;
import co.com.importcolex.tejeduria.web.rest.dto.FibrasTelaCrudaDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.FibrasTelaCrudaMapper;
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
 * REST controller for managing FibrasTelaCruda.
 */
@RestController
@RequestMapping("/api")
public class FibrasTelaCrudaResource {

    private final Logger log = LoggerFactory.getLogger(FibrasTelaCrudaResource.class);

    @Inject
    private FibrasTelaCrudaRepository fibrasTelaCrudaRepository;

    @Inject
    private FibrasTelaCrudaMapper fibrasTelaCrudaMapper;

    /**
     * POST  /fibrasTelaCrudas -> Create a new fibrasTelaCruda.
     */
    @RequestMapping(value = "/fibrasTelaCrudas",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FibrasTelaCrudaDTO> createFibrasTelaCruda(@Valid @RequestBody FibrasTelaCrudaDTO fibrasTelaCrudaDTO) throws URISyntaxException {
        log.debug("REST request to save FibrasTelaCruda : {}", fibrasTelaCrudaDTO);
        if (fibrasTelaCrudaDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new fibrasTelaCruda cannot already have an ID").body(null);
        }
        FibrasTelaCruda fibrasTelaCruda = fibrasTelaCrudaMapper.fibrasTelaCrudaDTOToFibrasTelaCruda(fibrasTelaCrudaDTO);
        FibrasTelaCruda result = fibrasTelaCrudaRepository.save(fibrasTelaCruda);
        return ResponseEntity.created(new URI("/api/fibrasTelaCrudas/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("fibrasTelaCruda", result.getId().toString()))
                .body(fibrasTelaCrudaMapper.fibrasTelaCrudaToFibrasTelaCrudaDTO(result));
    }

    /**
     * PUT  /fibrasTelaCrudas -> Updates an existing fibrasTelaCruda.
     */
    @RequestMapping(value = "/fibrasTelaCrudas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FibrasTelaCrudaDTO> updateFibrasTelaCruda(@Valid @RequestBody FibrasTelaCrudaDTO fibrasTelaCrudaDTO) throws URISyntaxException {
        log.debug("REST request to update FibrasTelaCruda : {}", fibrasTelaCrudaDTO);
        if (fibrasTelaCrudaDTO.getId() == null) {
            return createFibrasTelaCruda(fibrasTelaCrudaDTO);
        }
        FibrasTelaCruda fibrasTelaCruda = fibrasTelaCrudaMapper.fibrasTelaCrudaDTOToFibrasTelaCruda(fibrasTelaCrudaDTO);
        FibrasTelaCruda result = fibrasTelaCrudaRepository.save(fibrasTelaCruda);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("fibrasTelaCruda", fibrasTelaCrudaDTO.getId().toString()))
                .body(fibrasTelaCrudaMapper.fibrasTelaCrudaToFibrasTelaCrudaDTO(result));
    }

    /**
     * GET  /fibrasTelaCrudas -> get all the fibrasTelaCrudas.
     */
    @RequestMapping(value = "/fibrasTelaCrudas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<FibrasTelaCrudaDTO>> getAllFibrasTelaCrudas(Pageable pageable)
        throws URISyntaxException {
        Page<FibrasTelaCruda> page = fibrasTelaCrudaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fibrasTelaCrudas");
        return new ResponseEntity<>(page.getContent().stream()
            .map(fibrasTelaCrudaMapper::fibrasTelaCrudaToFibrasTelaCrudaDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /fibrasTelaCrudas/:id -> get the "id" fibrasTelaCruda.
     */
    @RequestMapping(value = "/fibrasTelaCrudas/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FibrasTelaCrudaDTO> getFibrasTelaCruda(@PathVariable Long id) {
        log.debug("REST request to get FibrasTelaCruda : {}", id);
        return Optional.ofNullable(fibrasTelaCrudaRepository.findOne(id))
            .map(fibrasTelaCrudaMapper::fibrasTelaCrudaToFibrasTelaCrudaDTO)
            .map(fibrasTelaCrudaDTO -> new ResponseEntity<>(
                fibrasTelaCrudaDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fibrasTelaCrudas/:id -> delete the "id" fibrasTelaCruda.
     */
    @RequestMapping(value = "/fibrasTelaCrudas/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFibrasTelaCruda(@PathVariable Long id) {
        log.debug("REST request to delete FibrasTelaCruda : {}", id);
        fibrasTelaCrudaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fibrasTelaCruda", id.toString())).build();
    }
}
