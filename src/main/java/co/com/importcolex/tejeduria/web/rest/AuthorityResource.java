package co.com.importcolex.tejeduria.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.importcolex.tejeduria.domain.Authority;
import co.com.importcolex.tejeduria.repository.AuthorityRepository;
import co.com.importcolex.tejeduria.web.rest.dto.AuthorityDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.AuthorityMapper;
import co.com.importcolex.tejeduria.web.rest.util.HeaderUtil;
import co.com.importcolex.tejeduria.web.rest.util.PaginationUtil;

import com.codahale.metrics.annotation.Timed;

@RestController
@RequestMapping("/api")
public class AuthorityResource {

	private final Logger log = LoggerFactory.getLogger(AuthorityResource.class);
	
	@Inject
    private AuthorityRepository authorityRepository;
	
	@Inject
	private AuthorityMapper authorityMapper;
	
	
	/**
     * POST  /authorities -> Create a new authoritie.
     */
    @RequestMapping(value = "/authorities",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthorityDTO> createAuthoritie(@Valid @RequestBody AuthorityDTO authorityDTO) throws URISyntaxException {
        log.debug("REST request to save Authoritie : {}", authorityDTO);
        if (authorityDTO.getName() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new authoritie cannot already have an name").body(null);
        }
        Authority authority = authorityMapper.authorityDTOToAuthority(authorityDTO);
        Authority result = authorityRepository.save(authority);
        return ResponseEntity.created(new URI("/api/authorities/" + result.getName()))
                .headers(HeaderUtil.createEntityCreationAlert("authoritie", result.getName().toString()))
                .body(authorityMapper.authorityToAuthorityDTO(result));
    }
    
    /**
     * PUT  /authorities -> Updates an existing authoritie.
     */
    @RequestMapping(value = "/authorities",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthorityDTO> updateAuthorities(@Valid @RequestBody AuthorityDTO authorityDTO) throws URISyntaxException {
        log.debug("REST request to update authorities : {}", authorityDTO);
        if (authorityDTO.getName() == null) {
            return createAuthoritie(authorityDTO);
        }
        Authority authority = authorityMapper.authorityDTOToAuthority(authorityDTO);
        Authority result = authorityRepository.save(authority);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("authoritie", authorityDTO.getName().toString()))
                .body(authorityMapper.authorityToAuthorityDTO(result));
    }
    
    /**
     * GET  /authorities -> get all the clientess.
     */
    @RequestMapping(value = "/authorities",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<AuthorityDTO>> getAllAuthorities(Pageable pageable)
        throws URISyntaxException {
        Page<Authority> page = authorityRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/authorities");
        return new ResponseEntity<>(page.getContent().stream()
            .map(authorityMapper::authorityToAuthorityDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }
}
