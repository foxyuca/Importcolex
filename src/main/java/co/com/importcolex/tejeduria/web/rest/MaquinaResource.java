package co.com.importcolex.tejeduria.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.com.importcolex.tejeduria.domain.Maquina;
import co.com.importcolex.tejeduria.repository.MaquinaRepository;
import co.com.importcolex.tejeduria.web.rest.util.HeaderUtil;
import co.com.importcolex.tejeduria.web.rest.util.PaginationUtil;
import co.com.importcolex.tejeduria.web.rest.dto.MaquinaDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.MaquinaMapper;
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
 * REST controller for managing Maquina.
 */
@RestController
@RequestMapping("/api")
public class MaquinaResource {

    private final Logger log = LoggerFactory.getLogger(MaquinaResource.class);

    @Inject
    private MaquinaRepository maquinaRepository;

    @Inject
    private MaquinaMapper maquinaMapper;

    /**
     * POST  /maquinas -> Create a new maquina.
     */
    @RequestMapping(value = "/maquinas",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MaquinaDTO> createMaquina(@Valid @RequestBody MaquinaDTO maquinaDTO) throws URISyntaxException {
        log.debug("REST request to save Maquina : {}", maquinaDTO);
        if (maquinaDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new maquina cannot already have an ID").body(null);
        }
        Maquina maquina = maquinaMapper.maquinaDTOToMaquina(maquinaDTO);
        Maquina result = maquinaRepository.save(maquina);
        return ResponseEntity.created(new URI("/api/maquinas/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("maquina", result.getId().toString()))
                .body(maquinaMapper.maquinaToMaquinaDTO(result));
    }

    /**
     * PUT  /maquinas -> Updates an existing maquina.
     */
    @RequestMapping(value = "/maquinas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MaquinaDTO> updateMaquina(@Valid @RequestBody MaquinaDTO maquinaDTO) throws URISyntaxException {
        log.debug("REST request to update Maquina : {}", maquinaDTO);
        if (maquinaDTO.getId() == null) {
            return createMaquina(maquinaDTO);
        }
        Maquina maquina = maquinaMapper.maquinaDTOToMaquina(maquinaDTO);
        Maquina result = maquinaRepository.save(maquina);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("maquina", maquinaDTO.getId().toString()))
                .body(maquinaMapper.maquinaToMaquinaDTO(result));
    }

    /**
     * GET  /maquinas -> get all the maquinas.
     */
    @RequestMapping(value = "/maquinas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<MaquinaDTO>> getAllMaquinas(Pageable pageable)
        throws URISyntaxException {
        Page<Maquina> page = maquinaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/maquinas");
        return new ResponseEntity<>(page.getContent().stream()
            .map(maquinaMapper::maquinaToMaquinaDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /maquinas/:id -> get the "id" maquina.
     */
    @RequestMapping(value = "/maquinas/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MaquinaDTO> getMaquina(@PathVariable Long id) {
        log.debug("REST request to get Maquina : {}", id);
        return Optional.ofNullable(maquinaRepository.findOne(id))
            .map(maquinaMapper::maquinaToMaquinaDTO)
            .map(maquinaDTO -> new ResponseEntity<>(
                maquinaDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /maquinas/:id -> delete the "id" maquina.
     */
    @RequestMapping(value = "/maquinas/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMaquina(@PathVariable Long id) {
        log.debug("REST request to delete Maquina : {}", id);
        maquinaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("maquina", id.toString())).build();
    }
}
