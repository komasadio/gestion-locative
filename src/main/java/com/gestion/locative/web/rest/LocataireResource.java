package com.gestion.locative.web.rest;

import com.gestion.locative.service.LocataireService;
import com.gestion.locative.web.rest.errors.BadRequestAlertException;
import com.gestion.locative.service.dto.LocataireDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.gestion.locative.domain.Locataire}.
 */
@RestController
@RequestMapping("/api")
public class LocataireResource {

    private final Logger log = LoggerFactory.getLogger(LocataireResource.class);

    private static final String ENTITY_NAME = "locataire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocataireService locataireService;

    public LocataireResource(LocataireService locataireService) {
        this.locataireService = locataireService;
    }

    /**
     * {@code POST  /locataires} : Create a new locataire.
     *
     * @param locataireDTO the locataireDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locataireDTO, or with status {@code 400 (Bad Request)} if the locataire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/locataires")
    public ResponseEntity<LocataireDTO> createLocataire(@RequestBody LocataireDTO locataireDTO) throws URISyntaxException {
        log.debug("REST request to save Locataire : {}", locataireDTO);
        if (locataireDTO.getId() != null) {
            throw new BadRequestAlertException("A new locataire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocataireDTO result = locataireService.save(locataireDTO);
        return ResponseEntity.created(new URI("/api/locataires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /locataires} : Updates an existing locataire.
     *
     * @param locataireDTO the locataireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locataireDTO,
     * or with status {@code 400 (Bad Request)} if the locataireDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locataireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/locataires")
    public ResponseEntity<LocataireDTO> updateLocataire(@RequestBody LocataireDTO locataireDTO) throws URISyntaxException {
        log.debug("REST request to update Locataire : {}", locataireDTO);
        if (locataireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocataireDTO result = locataireService.save(locataireDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locataireDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /locataires} : get all the locataires.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locataires in body.
     */
    @GetMapping("/locataires")
    public ResponseEntity<List<LocataireDTO>> getAllLocataires(Pageable pageable) {
        log.debug("REST request to get a page of Locataires");
        Page<LocataireDTO> page = locataireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /locataires/:id} : get the "id" locataire.
     *
     * @param id the id of the locataireDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locataireDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/locataires/{id}")
    public ResponseEntity<LocataireDTO> getLocataire(@PathVariable Long id) {
        log.debug("REST request to get Locataire : {}", id);
        Optional<LocataireDTO> locataireDTO = locataireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locataireDTO);
    }

    /**
     * {@code DELETE  /locataires/:id} : delete the "id" locataire.
     *
     * @param id the id of the locataireDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/locataires/{id}")
    public ResponseEntity<Void> deleteLocataire(@PathVariable Long id) {
        log.debug("REST request to delete Locataire : {}", id);
        locataireService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
