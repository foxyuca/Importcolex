package co.com.importcolex.tejeduria.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.com.importcolex.tejeduria.domain.Proveedores;
import co.com.importcolex.tejeduria.repository.ProveedoresRepository;
import co.com.importcolex.tejeduria.web.rest.util.HeaderUtil;
import co.com.importcolex.tejeduria.web.rest.util.PaginationUtil;
import co.com.importcolex.tejeduria.web.rest.dto.ProveedoresDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.ProveedoresMapper;
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
 * REST controller for managing Proveedores.
 */
@RestController
@RequestMapping("/api")
public class ProveedoresResource {

    private final Logger log = LoggerFactory.getLogger(ProveedoresResource.class);

    @Inject
    private ProveedoresRepository proveedoresRepository;

    @Inject
    private ProveedoresMapper proveedoresMapper;

    /**
     * POST  /proveedoress -> Create a new proveedores.
     */
    @RequestMapping(value = "/proveedoress",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProveedoresDTO> createProveedores(@Valid @RequestBody ProveedoresDTO proveedoresDTO) throws URISyntaxException {
        log.debug("REST request to save Proveedores : {}", proveedoresDTO);
        if (proveedoresDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new proveedores cannot already have an ID").body(null);
        }
        Proveedores proveedores = proveedoresMapper.proveedoresDTOToProveedores(proveedoresDTO);
        Proveedores result = proveedoresRepository.save(proveedores);
        return ResponseEntity.created(new URI("/api/proveedoress/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("proveedores", result.getId().toString()))
                .body(proveedoresMapper.proveedoresToProveedoresDTO(result));
    }

    /**
     * PUT  /proveedoress -> Updates an existing proveedores.
     */
    @RequestMapping(value = "/proveedoress",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProveedoresDTO> updateProveedores(@Valid @RequestBody ProveedoresDTO proveedoresDTO) throws URISyntaxException {
        log.debug("REST request to update Proveedores : {}", proveedoresDTO);
        if (proveedoresDTO.getId() == null) {
            return createProveedores(proveedoresDTO);
        }
        Proveedores proveedores = proveedoresMapper.proveedoresDTOToProveedores(proveedoresDTO);
        Proveedores result = proveedoresRepository.save(proveedores);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("proveedores", proveedoresDTO.getId().toString()))
                .body(proveedoresMapper.proveedoresToProveedoresDTO(result));
    }

    /**
     * GET  /proveedoress -> get all the proveedoress.
     */
    @RequestMapping(value = "/proveedoress",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ProveedoresDTO>> getAllProveedoress(Pageable pageable)
        throws URISyntaxException {
        Page<Proveedores> page = proveedoresRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/proveedoress");
        return new ResponseEntity<>(page.getContent().stream()
            .map(proveedoresMapper::proveedoresToProveedoresDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /proveedoress/:id -> get the "id" proveedores.
     */
    @RequestMapping(value = "/proveedoress/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProveedoresDTO> getProveedores(@PathVariable Long id) {
        log.debug("REST request to get Proveedores : {}", id);
        return Optional.ofNullable(proveedoresRepository.findOne(id))
            .map(proveedoresMapper::proveedoresToProveedoresDTO)
            .map(proveedoresDTO -> new ResponseEntity<>(
                proveedoresDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /proveedoress/:id -> delete the "id" proveedores.
     */
    @RequestMapping(value = "/proveedoress/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProveedores(@PathVariable Long id) {
        log.debug("REST request to delete Proveedores : {}", id);
        proveedoresRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("proveedores", id.toString())).build();
    }
}
