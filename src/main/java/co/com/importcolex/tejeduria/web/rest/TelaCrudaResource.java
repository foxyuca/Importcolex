package co.com.importcolex.tejeduria.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.com.importcolex.tejeduria.domain.TelaCruda;
import co.com.importcolex.tejeduria.repository.TelaCrudaRepository;
import co.com.importcolex.tejeduria.web.rest.util.HeaderUtil;
import co.com.importcolex.tejeduria.web.rest.util.PaginationUtil;
import co.com.importcolex.tejeduria.web.rest.dto.TelaCrudaDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.TelaCrudaMapper;
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
 * REST controller for managing TelaCruda.
 */
@RestController
@RequestMapping("/api")
public class TelaCrudaResource {

    private final Logger log = LoggerFactory.getLogger(TelaCrudaResource.class);

    @Inject
    private TelaCrudaRepository telaCrudaRepository;

    @Inject
    private TelaCrudaMapper telaCrudaMapper;

    /**
     * POST  /telaCrudas -> Create a new telaCruda.
     */
    @RequestMapping(value = "/telaCrudas",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TelaCrudaDTO> createTelaCruda(@Valid @RequestBody TelaCrudaDTO telaCrudaDTO) throws URISyntaxException {
        log.debug("REST request to save TelaCruda : {}", telaCrudaDTO);
        if (telaCrudaDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new telaCruda cannot already have an ID").body(null);
        }
        TelaCruda telaCruda = telaCrudaMapper.telaCrudaDTOToTelaCruda(telaCrudaDTO);
        TelaCruda result = telaCrudaRepository.save(telaCruda);
        return ResponseEntity.created(new URI("/api/telaCrudas/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("telaCruda", result.getId().toString()))
                .body(telaCrudaMapper.telaCrudaToTelaCrudaDTO(result));
    }

    /**
     * PUT  /telaCrudas -> Updates an existing telaCruda.
     */
    @RequestMapping(value = "/telaCrudas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TelaCrudaDTO> updateTelaCruda(@Valid @RequestBody TelaCrudaDTO telaCrudaDTO) throws URISyntaxException {
        log.debug("REST request to update TelaCruda : {}", telaCrudaDTO);
        if (telaCrudaDTO.getId() == null) {
            return createTelaCruda(telaCrudaDTO);
        }
        TelaCruda telaCruda = telaCrudaMapper.telaCrudaDTOToTelaCruda(telaCrudaDTO);
        TelaCruda result = telaCrudaRepository.save(telaCruda);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("telaCruda", telaCrudaDTO.getId().toString()))
                .body(telaCrudaMapper.telaCrudaToTelaCrudaDTO(result));
    }

    /**
     * GET  /telaCrudas -> get all the telaCrudas.
     */
    @RequestMapping(value = "/telaCrudas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<TelaCrudaDTO>> getAllTelaCrudas(Pageable pageable)
        throws URISyntaxException {
        Page<TelaCruda> page = telaCrudaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/telaCrudas");
        return new ResponseEntity<>(page.getContent().stream()
            .map(telaCrudaMapper::telaCrudaToTelaCrudaDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /telaCrudas/:id -> get the "id" telaCruda.
     */
    @RequestMapping(value = "/telaCrudas/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TelaCrudaDTO> getTelaCruda(@PathVariable Long id) {
        log.debug("REST request to get TelaCruda : {}", id);
        return Optional.ofNullable(telaCrudaRepository.findOne(id))
            .map(telaCrudaMapper::telaCrudaToTelaCrudaDTO)
            .map(telaCrudaDTO -> new ResponseEntity<>(
                telaCrudaDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /telaCrudas/:id -> delete the "id" telaCruda.
     */
    @RequestMapping(value = "/telaCrudas/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTelaCruda(@PathVariable Long id) {
        log.debug("REST request to delete TelaCruda : {}", id);
        telaCrudaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("telaCruda", id.toString())).build();
    }
}
